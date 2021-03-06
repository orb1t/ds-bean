package com.doctusoft.common.core.bean.binding.observable;

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

public class TestChangeListeners {
	
	@Test
	public void testSimpleAttributeChange() {
		TestBean bean1 = new TestBean("abc");
		TestBean bean2 = new TestBean("123");
		Bindings.bind(Bindings.obs(bean1).get(TestBean_._stringValue), Bindings.on(bean2).get(TestBean_._stringValue));
		assertEquals("abc", bean2.getStringValue());
		bean1.setStringValue("qwe");
		assertEquals("qwe", bean2.getStringValue());
	}

	@Test
	public void testChainedAttributeChange() {
		TestContainerBean container1 = new TestContainerBean(new TestBean("abc"));
		TestContainerBean container2 = new TestContainerBean(new TestBean("123"));
		Bindings.bind(Bindings.obs(container1).get(TestContainerBean_._testBean).get(TestBean_._stringValue),
				Bindings.on(container2).get(TestContainerBean_._testBean).get(TestBean_._stringValue));
		assertEquals("abc", container2.getTestBean().getStringValue());
		container1.getTestBean().setStringValue("qwe");
		assertEquals("qwe", container2.getTestBean().getStringValue());
	}

	@Test
	public void testBindingChainChange() {
		TestContainerBean container1 = new TestContainerBean(new TestBean("abc"));
		TestContainerBean container2 = new TestContainerBean(new TestBean("123"));
		Bindings.bind(Bindings.obs(container1).get(TestContainerBean_._testBean).get(TestBean_._stringValue),
				Bindings.on(container2).get(TestContainerBean_._testBean).get(TestBean_._stringValue));
		assertEquals("abc", container2.getTestBean().getStringValue());
		container1.setTestBean(new TestBean("qwe"));
		assertEquals("qwe", container2.getTestBean().getStringValue());
	}
}
