package com.aether.util;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

public class AetherUtils {

	@SuppressWarnings("unchecked")
	public static <T> ArrayList<T> getListOfPublicStaticFieldValuesIn(Class<?> containingClass, Class<T> fieldType) {
		Objects.requireNonNull(fieldType);
		ArrayList<T> values = new ArrayList<>();
		for (Field field : containingClass.getDeclaredFields()) {
			int mods = field.getModifiers();
			if (Modifier.isPublic(mods) && Modifier.isStatic(mods) && field.getType() == fieldType) {
				try {
					values.add((T)field.get(null));
				}
				catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> ArrayList<T> getListOfPublicStaticFieldValuesIn(Class<?> containingClass, Predicate<? super Type> fieldTypePredicate) {
		ArrayList<T> values = new ArrayList<>();
		for (Field field : containingClass.getDeclaredFields()) {
			int mods = field.getModifiers();
			if (Modifier.isPublic(mods) && Modifier.isStatic(mods) && fieldTypePredicate.test(field.getGenericType())) {
				try {
					values.add((T)field.get(null));
				}
				catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return values;
	}
	
	public static Class<?> getRawType(Type type) {
		if (type instanceof GenericArrayType) {
			GenericArrayType arrayType = (GenericArrayType)type;
			return getArrayTypeOf(getRawType(arrayType.getGenericComponentType()));
		}
		else if (type instanceof ParameterizedType) {
			return (Class<?>)((ParameterizedType)type).getRawType();
		}
		else if (type instanceof TypeVariable) {
			TypeVariable<?> typeVariable = (TypeVariable<?>)type;
			return getRawType(typeVariable.getBounds()[0]);
		}
		else if (type instanceof WildcardType) {
			WildcardType wildcardType = (WildcardType)type;
			return getRawType(wildcardType.getUpperBounds()[0]);
		}
		else {
			return (Class<?>)type;
		}
	}
	
	public static Class<?> getArrayTypeOf(Class<?> componentType) {
		if (componentType.isPrimitive()) {
			if (componentType == boolean.class) {
				return boolean[].class;
			}
			else if (componentType == byte.class) {
				return byte[].class;
			}
			else if (componentType == short.class) {
				return short[].class;
			}
			else if (componentType == char.class) {
				return char[].class;
			}
			else if (componentType == int.class) {
				return int[].class;
			}
			else if (componentType == long.class) {
				return long[].class;
			}
			else if (componentType == float.class) {
				return float[].class;
			}
			else if (componentType == double.class) {
				return double[].class;
			}
			else {
				return null;
			}
		}
		else {
			String name = componentType.getName();
			try {
				if (name.charAt(0) == '[') {
					return Class.forName('[' + name);
				}
				else {
					return Class.forName('[' + name + ';');
				}
			}
			catch (ClassNotFoundException e) {
				throw new AssertionError(e);
			}
		}
	}
	
	
	/**
	 * Does nothing.
	 */
	public static void evaluate(Object obj) {}
	
	/**
	 * Does nothing.
	 */
	public static void evaluate(boolean b) {}
	
	/**
	 * Does nothing.
	 */
	public static void evaluate(int i) {}
	
	/**
	 * Does nothing.
	 */
	public static void evaluate(long l) {}
	
	/**
	 * Does nothing.
	 */
	public static void evaluate(float f) {}
	
	/**
	 * Does nothing.
	 */
	public static void evaluate(double d) {}
	
}
