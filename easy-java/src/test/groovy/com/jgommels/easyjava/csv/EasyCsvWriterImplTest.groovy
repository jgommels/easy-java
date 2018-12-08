package com.jgommels.easyjava.csv

import com.google.common.jimfs.Configuration
import com.google.common.jimfs.Jimfs
import com.jgommels.easyjava.file.FileWriteMode
import com.jgommels.easyjava.strings.CaseFormat
import spock.lang.Specification

import java.nio.file.FileSystem
import java.nio.file.Files
import java.nio.file.Path
import java.time.Instant
import java.time.LocalDate

class EasyCsvWriterImplTest extends Specification {

    def 'test write CSV file' () {
        setup:
        FileSystem fs = Jimfs.newFileSystem(Configuration.unix())

        ItemOrder order1 = new ItemOrder()
        order1.setItemNumber(239201000001L)
        order1.setType(PurchaseType.ONLINE)
        order1.setName("30 inch LED TV")
        order1.setCustomerId(2)
        order1.setPurchaseDate(LocalDate.parse("2016-06-12"))
        order1.setPurchaseTimestamp(Instant.parse("2016-06-12T10:00:00Z"))
        order1.setQuantity(1)
        order1.setPricePerUnit(329.72)

        ItemOrder order2 = new ItemOrder()
        order2.setItemNumber(239201000002L)
        order2.setType(PurchaseType.IN_STORE)
        order2.setCustomerId(2)
        order2.setName("6' HDMI Cable")
        order2.setPurchaseDate(LocalDate.parse("2016-06-13"))
        order2.setPurchaseTimestamp(Instant.parse("2016-06-13T12:00:00Z"))
        order2.setQuantity(2)
        order2.setPricePerUnit(5.99)

        Path file = fs.getPath("/home/myuser/order.csv")
        Files.createDirectories(file.getParent())


        List<ItemOrder> orders = [order1, order2]

        EasyCsvWriter csvWriter = new EasyCsvWriterImpl()
        csvWriter.write(file, ItemOrder.class, orders, CaseFormat.LOWER_UNDERSCORE)

        when:
        List<String> lines = Files.readAllLines(file)

        then:
        lines.size() == 3
        lines.get(0) == "purchase_timestamp,purchase_date,customer_id,type,item_number,name,quantity,price_per_unit"
        lines.get(1) == "2016-06-12T10:00:00Z,2016-06-12,2,ONLINE,239201000001,30 inch LED TV,1,329.72"
        lines.get(2) == "2016-06-13T12:00:00Z,2016-06-13,2,IN_STORE,239201000002,6' HDMI Cable,2,5.99"
    }

    def 'test append CSV file' () {
        setup:
        FileSystem fs = Jimfs.newFileSystem(Configuration.unix())

        ItemOrder order1 = new ItemOrder()
        order1.setItemNumber(239201000001L)
        order1.setType(PurchaseType.ONLINE)
        order1.setName("30 inch LED TV")
        order1.setCustomerId(2)
        order1.setPurchaseDate(LocalDate.parse("2016-06-12"))
        order1.setPurchaseTimestamp(Instant.parse("2016-06-12T10:00:00Z"))
        order1.setQuantity(1)
        order1.setPricePerUnit(329.72)

        ItemOrder order2 = new ItemOrder()
        order2.setItemNumber(239201000002L)
        order2.setType(PurchaseType.IN_STORE)
        order2.setCustomerId(2)
        order2.setName("6' HDMI Cable")
        order2.setPurchaseDate(LocalDate.parse("2016-06-13"))
        order2.setPurchaseTimestamp(Instant.parse("2016-06-13T12:00:00Z"))
        order2.setQuantity(2)
        order2.setPricePerUnit(5.99)

        Path file = fs.getPath("/home/myuser/order.csv")
        Files.createDirectories(file.getParent())

        EasyCsvWriter csvWriter = new EasyCsvWriterImpl()
        csvWriter.write(file, ItemOrder.class, [order1], FileWriteMode.APPEND)
        csvWriter.write(file, ItemOrder.class, [order2], FileWriteMode.APPEND)

        when:
        List<String> lines = Files.readAllLines(file)

        then:
        lines.size() == 3
        lines.get(0) == "purchase_timestamp,purchase_date,customer_id,type,item_number,name,quantity,price_per_unit"
        lines.get(1) == "2016-06-12T10:00:00Z,2016-06-12,2,ONLINE,239201000001,30 inch LED TV,1,329.72"
        lines.get(2) == "2016-06-13T12:00:00Z,2016-06-13,2,IN_STORE,239201000002,6' HDMI Cable,2,5.99"
    }
}
