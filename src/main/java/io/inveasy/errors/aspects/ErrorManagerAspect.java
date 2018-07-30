/*
 * Copyright 2018 Guillaume Gravetot
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.inveasy.errors.aspects;

import io.inveasy.errors.annotations.ClassErrorPrefix;
import io.inveasy.errors.annotations.MethodErrorPrefix;
import io.inveasy.errors.annotations.ProjectErrorPrefix;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.lang.annotation.Annotation;

@Aspect
public class ErrorManagerAspect
{
	private String projectPrefix = "";
	
	@Before("execution(public static void main(..))")
	public void beforeMain(JoinPoint joinPoint)
	{
		Annotation a = joinPoint.getSignature().getDeclaringType(). getAnnotation(ProjectErrorPrefix.class);
		
		if(a != null)
			projectPrefix = ((ProjectErrorPrefix)a).value();
	}
	
	@Around("call(public static String io.inveasy.errors.ErrorManager.code(String)) && @within(classErrorPrefix) && @withincode(methodErrorPrefix)")
	public Object aroundErrorManagerCode(ProceedingJoinPoint joinPoint, ClassErrorPrefix classErrorPrefix, MethodErrorPrefix methodErrorPrefix)
	{
		return projectPrefix + classErrorPrefix.value() + methodErrorPrefix.value() + joinPoint.getArgs()[0];
	}
	
	@Around("call(public static String io.inveasy.errors.ErrorManager.code(String)) && @within(classErrorPrefix) && !@withincode(io.inveasy.errors.annotations.MethodErrorPrefix)")
	public Object aroundErrorManagerCode(ProceedingJoinPoint joinPoint, ClassErrorPrefix classErrorPrefix)
	{
		return projectPrefix + classErrorPrefix.value() + joinPoint.getArgs()[0];
	}
}
