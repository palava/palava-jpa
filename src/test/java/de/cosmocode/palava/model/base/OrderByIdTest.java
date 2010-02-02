package de.cosmocode.palava.model.base;

import java.util.Collections;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.inject.internal.Lists;

/**
 * Tests {@link EntityBase#ORDER_BY_ID}.
 *
 * @author Willi Schoenborn
 */
public final class OrderByIdTest {

    /**
     * Tests ordering of 3 entities.
     */
    @Test
    public void test() {
        final List<EntityBase> list = Lists.newArrayList();
        
        final EntityBase first = EasyMock.createMock("first", EntityBase.class);
        EasyMock.expect(first.getId()).andStubReturn(1L);
        list.add(first);
        
        final EntityBase second = EasyMock.createMock("second", EntityBase.class);
        EasyMock.expect(second.getId()).andStubReturn(Long.MAX_VALUE);
        list.add(second);
        
        final EntityBase third = EasyMock.createMock("third", EntityBase.class);
        EasyMock.expect(third.getId()).andStubReturn(10000L);
        list.add(third);
        
        EasyMock.replay(first, second, third);
        
        Collections.sort(list, EntityBase.ORDER_BY_ID);
        
        Assert.assertEquals(ImmutableList.of(first, third, second), list);
    }

}
