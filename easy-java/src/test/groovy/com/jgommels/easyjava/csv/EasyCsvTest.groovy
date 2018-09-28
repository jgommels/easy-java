package com.jgommels.easyjava.csv

import com.google.common.jimfs.Configuration
import com.google.common.jimfs.Jimfs
import com.jgommels.easyjava.file.FileWriteMode
import com.jgommels.easyjava.strings.CaseFormat
import org.junit.Assert
import spock.lang.Specification

import java.nio.file.FileSystem
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Instant
import java.time.LocalDate

class EasyCsvTest extends Specification{

    def 'test read CSV file'() {
        setup:
        EasyCsv easyCsv = new EasyCsv()

        when:
        List<ItemOrder> orders = easyCsv.read(getClass().getResourceAsStream("/orders.csv"), ItemOrder.class)

        then:
        Assert.assertEquals(2, orders.size())

        ItemOrder order1 = orders.get(0)
        Assert.assertEquals(Instant.parse("2016-06-12T10:00:00Z"), order1.getPurchaseTimestamp())
        Assert.assertEquals(LocalDate.parse("2016-06-12"), order1.getPurchaseDate())
        Assert.assertEquals(1, order1.getCustomerId())
        Assert.assertEquals(239201000001L, order1.getItemNumber())
        Assert.assertEquals("30 inch LED TV",order1.getName())
        Assert.assertEquals(1, order1.getQuantity())
        Assert.assertEquals(329.72, order1.getPricePerUnit(), 0.0001)

        ItemOrder order2 = orders.get(1)
        Assert.assertEquals(Instant.parse("2016-06-13T12:00:00Z"), order2.getPurchaseTimestamp())
        Assert.assertEquals(LocalDate.parse("2016-06-13"), order2.getPurchaseDate())
        Assert.assertEquals(1, order2.getCustomerId())
        Assert.assertEquals(239201000002L, order2.getItemNumber())
        Assert.assertEquals("6' HDMI Cable",order2.getName())
        Assert.assertEquals(2, order2.getQuantity())
        Assert.assertEquals(5.99, order2.getPricePerUnit(), 0.0001)
    }

    def 'test tail CSV file'() {
        setup:
        EasyCsv easyCsv = new EasyCsv()
        print(Paths.get("test/resources/orders.csv").toAbsolutePath())

        when:
        List<ItemOrder> orders = easyCsv.tail(Paths.get("./src/test/resources/orders.csv"), ItemOrder.class, 1)

        then:
        Assert.assertEquals(1, orders.size())

        ItemOrder lastOrder = orders.get(0)
        Assert.assertEquals(Instant.parse("2016-06-13T12:00:00Z"), lastOrder.getPurchaseTimestamp())
        Assert.assertEquals(LocalDate.parse("2016-06-13"), lastOrder.getPurchaseDate())
        Assert.assertEquals(1, lastOrder.getCustomerId())
        Assert.assertEquals(239201000002L, lastOrder.getItemNumber())
        Assert.assertEquals("6' HDMI Cable",lastOrder.getName())
        Assert.assertEquals(2, lastOrder.getQuantity())
        Assert.assertEquals(5.99, lastOrder.getPricePerUnit(), 0.0001)
    }

    def 'test write CSV file' () {
        setup:
        FileSystem fs = Jimfs.newFileSystem(Configuration.unix())

        ItemOrder order1 = new ItemOrder()
        order1.setItemNumber(239201000001L)
        order1.setName("30 inch LED TV")
        order1.setCustomerId(2)
        order1.setPurchaseDate(LocalDate.parse("2016-06-12"))
        order1.setPurchaseTimestamp(Instant.parse("2016-06-12T10:00:00Z"))
        order1.setQuantity(1)
        order1.setPricePerUnit(329.72)

        ItemOrder order2 = new ItemOrder()
        order2.setItemNumber(239201000002L)
        order2.setCustomerId(2)
        order2.setName("6' HDMI Cable")
        order2.setPurchaseDate(LocalDate.parse("2016-06-13"))
        order2.setPurchaseTimestamp(Instant.parse("2016-06-13T12:00:00Z"))
        order2.setQuantity(2)
        order2.setPricePerUnit(5.99)

        Path file = fs.getPath("/home/myuser/order.csv")
        Files.createDirectories(file.getParent())


        List<ItemOrder> orders = [order1, order2]

        EasyCsv easyCsv = new EasyCsv()
        easyCsv.write(file, ItemOrder.class, orders, CaseFormat.LOWER_UNDERSCORE)

        String expectedLine1 = "purchase_timestamp,purchase_date,customer_id,item_number,name,quantity,price_per_unit"
        String expectedLine2 = "2016-06-12T10:00:00Z,2016-06-12,2,239201000001,30 inch LED TV,1,329.72"
        String expectedLine3 = "2016-06-13T12:00:00Z,2016-06-13,2,239201000002,6' HDMI Cable,2,5.99"

        when:
            List<String> lines = Files.readAllLines(file)

        then:
            Assert.assertEquals(3, lines.size())
            Assert.assertEquals(expectedLine1, lines.get(0))
            Assert.assertEquals(expectedLine2, lines.get(1))
            Assert.assertEquals(expectedLine3, lines.get(2))
    }

    def 'test append CSV file' () {
        setup:
        FileSystem fs = Jimfs.newFileSystem(Configuration.unix())

        ItemOrder order1 = new ItemOrder()
        order1.setItemNumber(239201000001L)
        order1.setName("30 inch LED TV")
        order1.setCustomerId(2)
        order1.setPurchaseDate(LocalDate.parse("2016-06-12"))
        order1.setPurchaseTimestamp(Instant.parse("2016-06-12T10:00:00Z"))
        order1.setQuantity(1)
        order1.setPricePerUnit(329.72)

        ItemOrder order2 = new ItemOrder()
        order2.setItemNumber(239201000002L)
        order2.setCustomerId(2)
        order2.setName("6' HDMI Cable")
        order2.setPurchaseDate(LocalDate.parse("2016-06-13"))
        order2.setPurchaseTimestamp(Instant.parse("2016-06-13T12:00:00Z"))
        order2.setQuantity(2)
        order2.setPricePerUnit(5.99)

        Path file = fs.getPath("/home/myuser/order.csv")
        Files.createDirectories(file.getParent())

        EasyCsv easyCsv = new EasyCsv()
        easyCsv.write(file, ItemOrder.class, [order1], FileWriteMode.APPEND)
        easyCsv.write(file, ItemOrder.class, [order2], FileWriteMode.APPEND)

        String expectedLine1 = "purchase_timestamp,purchase_date,customer_id,item_number,name,quantity,price_per_unit"
        String expectedLine2 = "2016-06-12T10:00:00Z,2016-06-12,2,239201000001,30 inch LED TV,1,329.72"
        String expectedLine3 = "2016-06-13T12:00:00Z,2016-06-13,2,239201000002,6' HDMI Cable,2,5.99"

        when:
        List<String> lines = Files.readAllLines(file)

        then:
        Assert.assertEquals(3, lines.size())
        Assert.assertEquals(expectedLine1, lines.get(0))
        Assert.assertEquals(expectedLine2, lines.get(1))
        Assert.assertEquals(expectedLine3, lines.get(2))
    }
}
