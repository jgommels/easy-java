package com.jgommels.easyjava.csv

import com.jgommels.easyjava.bean.BeanUtilsBeanFactory
import org.junit.Assert
import spock.lang.Specification

import java.nio.file.Paths
import java.time.Instant
import java.time.LocalDate

class EasyCsvReaderImplTest extends Specification {

    def 'test read CSV file'() {
        setup:
        EasyCsvReader csvReader = new EasyCsvReaderImpl(BeanUtilsBeanFactory.beanUtilsBean)

        when:
        List<ItemOrder> orders = csvReader.read(getClass().getResourceAsStream("/orders.csv"), ItemOrder.class)
        ItemOrder order1 = orders.get(0)
        ItemOrder order2 = orders.get(1)

        then:
        orders.size() == 2

        order1.getPurchaseTimestamp() == Instant.parse("2016-06-12T10:00:00Z")
        order1.getPurchaseDate() == LocalDate.parse("2016-06-12")
        order1.getCustomerId() == 1
        order1.getType() == PurchaseType.ONLINE
        order1.getItemNumber() == 239201000001L
        order1.getName() == "30 inch LED TV"
        order1.getQuantity() == 1
        Assert.assertEquals(329.72, order1.getPricePerUnit(), 0.0001)

        order2.getPurchaseTimestamp() == Instant.parse("2016-06-13T12:00:00Z")
        order2.getPurchaseDate() == LocalDate.parse("2016-06-13")
        order2.getCustomerId() == 1
        order2.getType() == PurchaseType.IN_STORE
        order2.getItemNumber() == 239201000002L
        order2.getName() == "6' HDMI Cable"
        order2.getQuantity() == 2
        Assert.assertEquals(5.99, order2.getPricePerUnit(), 0.0001)
    }

    def 'test tail CSV file'() {
        setup:
        EasyCsvReader csvReader = new EasyCsvReaderImpl(BeanUtilsBeanFactory.beanUtilsBean)

        when:
        List<ItemOrder> orders = csvReader.tail(Paths.get("./src/test/resources/orders.csv"), ItemOrder.class, 1)

        then:
        Assert.assertEquals(1, orders.size())

        ItemOrder lastOrder = orders.get(0)
        lastOrder.getPurchaseTimestamp() == Instant.parse("2016-06-13T12:00:00Z")
        lastOrder.getPurchaseDate() == LocalDate.parse("2016-06-13")
        lastOrder.getCustomerId() == 1
        lastOrder.getItemNumber() == 239201000002L
        lastOrder.getName() == "6' HDMI Cable"
        lastOrder.getQuantity() == 2
        Assert.assertEquals(5.99, lastOrder.getPricePerUnit(), 0.0001)
    }

}
