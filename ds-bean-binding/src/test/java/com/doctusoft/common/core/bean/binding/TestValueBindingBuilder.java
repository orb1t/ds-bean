package com.doctusoft.common.core.bean.binding;

/*
 * #%L
 * ds-bean-binding
 * %%
 * Copyright (C) 2014 Doctusoft Ltd.
 * %%
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
 * #L%
 */


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.doctusoft.bean.binding.Bindings;
import com.doctusoft.bean.binding.ConstantValueBinding;
import com.doctusoft.bean.binding.Converter;
import com.doctusoft.bean.binding.ValueBinding;

public class TestValueBindingBuilder {

	@Test
	public void testConstantValueBinding() {
		String string = "hello world";
		ValueBinding<String> binding = Bindings.on(string);
		assertEquals(string, binding.getValue());
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testWriteConstantValueBinding() {
		String string = "hello world";
		ValueBinding<String> binding = Bindings.on(string);
		binding.setValue("");
	}
	
	@Test
	public void testSimpleAttributeValueBinding() {
		String string = "hello world";
		TestBean testBean = new TestBean(string);
		ValueBinding<String> binding = Bindings.on(testBean).get(TestBean_._stringValue);
		assertEquals(string, binding.getValue());
		String newvalue = "newvalue";
		binding.setValue(newvalue);
		assertEquals(newvalue, binding.getValue());
		assertEquals(newvalue, testBean.getStringValue());
	}
	
	@Test
	public void testChainedAttributeValueBinding() {
		String string = "hello world";
		TestContainerBean testContainerBean = new TestContainerBean(new TestBean(string));
		ValueBinding<String> binding = Bindings
				.on(testContainerBean)
				.get(TestContainerBean_._testBean)
				.get(TestBean_._stringValue);
		assertEquals(string, binding.getValue());
		String newvalue = "newvalue";
		binding.setValue(newvalue);
		assertEquals(newvalue, binding.getValue());
		assertEquals(newvalue, testContainerBean.getTestBean().getStringValue());
	}
	
	@Test
	public void testConvertingValueBinding() {
		String string = "1";
		TestBean testBean = new TestBean(string);
		ValueBinding<Integer> binding = Bindings
					.on(testBean)
					.get(TestBean_._stringValue)
					.convert(new DummyConverter());
		assertEquals(new Integer(1), binding.getValue());
		binding.setValue(2);
		assertEquals("2", testBean.getStringValue());
	}
	
	@Test
	public void testBuilderFromExistingBinding() {
		TestBean testBean = new TestBean("1");
		ValueBinding<TestBean> sourceBinding = new ConstantValueBinding<TestBean>(testBean);
		ValueBinding<String> binding = Bindings.from(sourceBinding).get(TestBean_._stringValue);
		assertEquals("1", binding.getValue());
	}
	
	protected class DummyConverter implements Converter<String, Integer> {
		@Override
		public Integer convertSource(String source) {
			return Integer.parseInt(source);
		}
		@Override
		public String convertTarget(Integer target) {
			return (target == null)?null:target.toString();
		}
	}
}
