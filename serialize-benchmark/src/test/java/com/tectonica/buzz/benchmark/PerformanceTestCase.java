package com.tectonica.buzz.benchmark;

import com.tectonica.model.generic.Primitives;

public abstract class PerformanceTestCase
{
	private final String name;
	private final int repetitions;
	private final Primitives testInput;
	private Primitives testOutput;
	private long writeTimeNanos;
	private long readTimeNanos;
	private long objectSize;

	public PerformanceTestCase(final String name, final int repetitions, final Primitives testInput)
	{
		this.name = name;
		this.repetitions = repetitions;
		this.testInput = testInput;
	}

	public void init() throws Exception
	{}

	public String getName()
	{
		return name;
	}

	public Primitives getTestOutput()
	{
		return testOutput;
	}

	public long getWriteTimeNanos()
	{
		return writeTimeNanos;
	}

	public long getReadTimeNanos()
	{
		return readTimeNanos;
	}

	public long getObjectSize()
	{
		return objectSize;
	}

	public void setObjectSize(long objectSize)
	{
		this.objectSize = objectSize;
	}

	public void performTest() throws Exception
	{
		final long startWriteNanos = System.nanoTime();
		testWrite(testInput);
		writeTimeNanos = (System.nanoTime() - startWriteNanos) / repetitions;

		final long startReadNanos = System.nanoTime();
		testOutput = testRead();
		readTimeNanos = (System.nanoTime() - startReadNanos) / repetitions;
	}

	public abstract void testWrite(Primitives item) throws Exception;

	public abstract Primitives testRead() throws Exception;
}
