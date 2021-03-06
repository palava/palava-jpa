/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.palava.model.base;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * Tests {@link EntityBase#ORDER_BY_MODIFICATION}.
 *
 * @author Willi Schoenborn
 */
public final class OrderByModificationTest {

    /**
     * Tests ordering of 3 entities.
     */
    @Test
    public void order() {
        final List<EntityBase> list = Lists.newArrayList();
        
        final EntityBase first = EasyMock.createMock("first", EntityBase.class);
        EasyMock.expect(first.getModifiedAt()).andStubReturn(null);
        list.add(first);
        
        final EntityBase second = EasyMock.createMock("second", EntityBase.class);
        EasyMock.expect(second.getModifiedAt()).andStubReturn(
            new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)));
        list.add(second);
        
        final EntityBase third = EasyMock.createMock("third", EntityBase.class);
        EasyMock.expect(third.getModifiedAt()).andStubReturn(new Date());
        list.add(third);
        
        EasyMock.replay(first, second, third);
        
        Collections.sort(list, EntityBase.ORDER_BY_MODIFICATION);
        
        Assert.assertEquals(ImmutableList.of(third, second, first), list);
        EasyMock.verify(first, second, third);
    }
    
}
