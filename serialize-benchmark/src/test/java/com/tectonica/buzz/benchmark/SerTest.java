package com.tectonica.buzz.benchmark;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.junit.Test;
import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.FastInput;
import com.esotericsoftware.kryo.io.FastOutput;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.tectonica.buzz.BUZZ;
import com.tectonica.buzz.io.BuzzDataInput;
import com.tectonica.buzz.io.BuzzDataOutput;
import com.tectonica.buzz.stream.BuzzInputStreamFast;
import com.tectonica.buzz.stream.BuzzOutputStreamFast;
import com.tectonica.buzzers.Buzzers;
import com.tectonica.model.generic.Primitives;
import com.tectonica.serialize.ExtObjectInput;
import com.tectonica.serialize.ExtObjectOutput;

public final class SerTest
{
	public static final int REPETITIONS = 1 * 100 * 1000;

	private Primitives ITEM = Primitives.generate();

	@Test
	public void test() throws Exception
	{
		for (final PerformanceTestCase testCase : testCases)
		{
			testCase.init();
			for (int i = 0; i < 4; i++)
			{
				testCase.performTest();

				System.out.format("%d  |  %-15s  |  write %,6d ns  |  read %,6d ns  |  total %,6d ns  |  size=%,5d bytes\n", i,
						testCase.getName(), testCase.getWriteTimeNanos(), testCase.getReadTimeNanos(), testCase.getWriteTimeNanos()
								+ testCase.getReadTimeNanos(), testCase.getObjectSize());

				if (!ITEM.equals(testCase.getTestOutput()))
					throw new IllegalStateException("Objects do not match");

				System.gc();
				Thread.sleep(1000);
				System.gc();
				Thread.sleep(500);
			}
			System.out
					.println("------------------------------------------------------------------------------------------------------------------------");
		}
	}

	private final PerformanceTestCase[] testCases = { //

	new PerformanceTestCase("Externalization", REPETITIONS, ITEM)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		public void testWrite(Primitives item) throws Exception
		{
			for (int i = 0; i < REPETITIONS; i++)
			{
				baos.reset();
				ObjectOutput oos = new ObjectOutputStream(baos);
				oos.writeObject(item);
			}
			setObjectSize(baos.size());
		}

		public Primitives testRead() throws Exception
		{
			byte[] bytes = baos.toByteArray();
			Primitives object = null;
			for (int i = 0; i < REPETITIONS; i++)
			{
				ObjectInput ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
				object = (Primitives) ois.readObject();
				ois.close();
			}

			return object;
		}
	},

	new PerformanceTestCase("EXTERN", REPETITIONS, ITEM)
	{
		BuzzOutputStreamFast output = new BuzzOutputStreamFast(1024, -1);

		public void testWrite(Primitives item) throws Exception
		{
			for (int i = 0; i < REPETITIONS; i++)
			{
				output.clear();
				ObjectOutput oos = new ExtObjectOutput(output);
				oos.writeObject(item);
				oos.close();
			}
			setObjectSize(output.total());
		}

		public Primitives testRead() throws Exception
		{
			Primitives object = null;
			for (int i = 0; i < REPETITIONS; i++)
			{
				BuzzInputStreamFast input = new BuzzInputStreamFast(output.getBuffer(), 0, output.position());
				ObjectInput ois = new ExtObjectInput<Primitives>(input, Primitives.class);
				object = (Primitives) ois.readObject();
				ois.close();
			}

			return object;
		}
	},

	new PerformanceTestCase("BUZZ", REPETITIONS, ITEM)
	{
		BUZZ buzz;
		BuzzOutputStreamFast output;

		public void init()
		{
			buzz = new BUZZ();
			Buzzers.registerBuzzers(buzz);

			output = new BuzzOutputStreamFast(1024, -1);
		}

		public void testWrite(Primitives item) throws Exception
		{
			for (int i = 0; i < REPETITIONS; i++)
			{
				output.clear();
				DataOutput oos = new BuzzDataOutput(output);
				buzz.writeObject(oos, item);
			}
			setObjectSize(output.total());
		}

		public Primitives testRead() throws Exception
		{
			Primitives object = null;
			for (int i = 0; i < REPETITIONS; i++)
			{
				BuzzInputStreamFast input = new BuzzInputStreamFast(output.getBuffer(), 0, output.position());
				DataInput ois = new BuzzDataInput(input);
				object = buzz.readObject(ois, Primitives.class);
			}

			return object;
		}
	},

//	new PerformanceTestCase("ByteBuffer", REPETITIONS, ITEM)
//	{
//		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//
//		public void testWrite(Primitives item) throws Exception
//		{
//			for (int i = 0; i < REPETITIONS; i++)
//			{
//				byteBuffer.clear();
//				item.write(byteBuffer);
//			}
//			setObjectSize(byteBuffer.position());
//		}
//
//		public Primitives testRead() throws Exception
//		{
//			Primitives object = null;
//			for (int i = 0; i < REPETITIONS; i++)
//			{
//				byteBuffer.flip();
//				object = Primitives.read(byteBuffer);
//			}
//
//			return object;
//		}
//	},

			new PerformanceTestCase("Kryo", REPETITIONS, ITEM)
			{
				Kryo kryo;
//				Output output = new UnsafeOutput(1024, -1);
				Output output = new FastOutput(1024, -1);

				public void init()
				{
					kryo = new Kryo();
					kryo.setReferences(false);
//			kryo.setDefaultSerializer(CompatibleFieldSerializer.class);
//			kryo.register(Primitives.class, 50);
				}

				public void testWrite(Primitives item) throws Exception
				{
					for (int i = 0; i < REPETITIONS; i++)
					{
						output.clear();
						kryo.writeObject(output, item);
					}
					setObjectSize(output.total());
				}

				public Primitives testRead() throws Exception
				{
					Primitives object = null;
					for (int i = 0; i < REPETITIONS; i++)
					{
//						Input input = new UnsafeInput(output.getBuffer(), 0, output.position());
						Input input = new FastInput(output.getBuffer(), 0, output.position());
						object = kryo.readObject(input, Primitives.class);
					}

					return object;
				}
			},

//			new PerformanceTestCase("GridGain", REPETITIONS, ITEM)
//			{
//				GridMarshaller marsh;
//
//				public void init() throws Exception
//				{
////			marsh = new GridOptimizedMarshaller(true);
//					marsh = new GridOptimizedMarshaller(false, Arrays.asList(Primitives.class.getName()), null, 0);
//				}
//
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//				public void testWrite(Primitives item) throws Exception
//				{
//					for (int i = 0; i < REPETITIONS; i++)
//					{
//						baos.reset();
//						marsh.marshal(item, baos);
//					}
//					setObjectSize(baos.size());
//				}
//
//				public Primitives testRead() throws Exception
//				{
//					Primitives object = null;
//					byte[] bytes = baos.toByteArray();
//					for (int i = 0; i < REPETITIONS; i++)
//					{
//						object = marsh.unmarshal(bytes, null);
//					}
//
//					return object;
//				}
//			},

			new PerformanceTestCase("FST", REPETITIONS, ITEM)
			{
				FSTConfiguration conf;

				public void init() throws Exception
				{
					conf = FSTConfiguration.createDefaultConfiguration();
					conf.setShareReferences(false);
				}

				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				public void testWrite(Primitives item) throws Exception
				{
					for (int i = 0; i < REPETITIONS; i++)
					{
						baos.reset();

						FSTObjectOutput oos = conf.getObjectOutput(baos);
						oos.writeObject(item);
						oos.flush();
					}
					setObjectSize(baos.size());
				}

				public Primitives testRead() throws Exception
				{
					byte[] bytes = baos.toByteArray();
					Primitives object = null;
					for (int i = 0; i < REPETITIONS; i++)
					{
						FSTObjectInput ois = conf.getObjectInput(bytes);
						object = (Primitives) ois.readObject();
					}

					return object;
				}
			}

	};
}
