/* Copyright 2013-2014 SpringSource.
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
package grails.plugin.springsecurity.access.intercept;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.intercept.AfterInvocationManager;
import org.springframework.security.core.Authentication;

/**
 * No-op implementation.
 *
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
public class NullAfterInvocationManager implements AfterInvocationManager {

	public Object decide(Authentication a, Object o, Collection<ConfigAttribute> attrs, Object returnedObject) {
		return returnedObject;
	}

	public boolean supports(ConfigAttribute attribute) {
		return false;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
}
