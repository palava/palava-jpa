package de.cosmocode.palava.model.base;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.inject.internal.Lists;

/**
 * Tests {@link EntityBase#ORDER_BY_AGE}.
 *
 * @author Willi Schoenborn
 */
public final class OrderByAgeTest {

    /**
     * Tests ordering of 3 entities.
     */
    @Test
    public void test() {
        final List<EntityBase> list = Lists.newArrayList();
        
        final EntityBase first = EasyMock.createMock("first", EntityBase.class);
        EasyMock.expect(first.getCreatedAt()).andStubReturn(null);
        list.add(first);
        
        final EntityBase second = EasyMock.createMock("second", EntityBase.class);
        EasyMock.expect(second.getCreatedAt()).andStubReturn(
            new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(10)));
        list.add(second);
        
        final EntityBase third = EasyMock.createMock("third", EntityBase.class);
        EasyMock.expect(third.getCreatedAt()).andStubReturn(new Date());
        list.add(third);
        
        EasyMock.replay(first, second, third);
        
        Collections.sort(list, EntityBase.ORDER_BY_AGE);
        
        Assert.assertEquals(ImmutableList.of(second, third, first), list);
    }

}
