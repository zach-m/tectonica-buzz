package com.tectonica.buzz;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

@SupportedAnnotationTypes("com.tectonica.buzz.Buzzable")
public final class BuzzProcessor extends AbstractProcessor
{
	@Override
	public SourceVersion getSupportedSourceVersion()
	{
		return SourceVersion.latestSupported();
	}

	@Override
	public boolean process(Set<? extends TypeElement> types, RoundEnvironment env)
	{
		System.out.println("ROUND ---------------------------------------------------------------------------------------------");
		for (Element elem : env.getElementsAnnotatedWith(Buzzable.class))
		{
			if (elem.getKind() != ElementKind.CLASS)
			{
				warn("Can't declare " + elem.asType().toString() + " as Buzzable, only classes are allowed", elem);
				continue;
			}

			printElem(elem, 0);
		}
		return true;
	}

	private void printElem(Element elem, int depth)
	{
		for (int i = 0; i < depth; i++)
			System.out.print(" ");
		System.out.println(elem.getKind().name() + " <<" + elem.asType().toString() + ">> " + elem.getSimpleName().toString() + " "
				+ elem.getModifiers().toString());

		final List<? extends Element> encElems = elem.getEnclosedElements();
		if (encElems == null)
			return;
		for (Element encElem : encElems)
			printElem(encElem, depth + 4);
	}

	private void warn(final String msg, Element elem)
	{
		processingEnv.getMessager().printMessage(Kind.MANDATORY_WARNING, msg, elem);
	}

	private void error(String msg, Element elem)
	{
		processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, elem);
	}

	private void gen(String className) throws IOException
	{
		JavaFile javaFile = xxx(className);

		javaFile.writeTo(processingEnv.getFiler());
	}

	private static JavaFile xxx(String className)
	{
		MethodSpec main = MethodSpec.methodBuilder("main").addModifiers(Modifier.PUBLIC, Modifier.STATIC).returns(void.class)
				.addParameter(String[].class, "args").addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!").build();

		TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld" + className).addModifiers(Modifier.PUBLIC, Modifier.FINAL).addMethod(main)
				.build();

		JavaFile javaFile = JavaFile.builder("com.tectonica.buzz", helloWorld).build();
		return javaFile;
	}

	public static void main(String[] args)
	{
		System.out.println(xxx("X").toString());
	}
}
