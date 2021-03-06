# easy-java

*Author: Jared Gommels*

This is a small library that so far provides basic support for the following:
- Easily read and write CSV files in a single line of code by converting to/from POJOs
- Convert between different case formats
- Other miscellaneous utility classes

This library requires Java 11 or higher.

**Warning: This library has not had any official versioned release. Do not use this in production code.**

## Reading CSV Files

Assume we have a file like the following:

    purchase_timestamp,purchase_date,customer_id,type,item_number,name,quantity,price_per_unit
    2016-06-12T10:00:00Z,2016-06-12,1,ONLINE,239201000001,30 inch LED TV,1,329.72
    2016-06-13T12:00:00Z,2016-06-13,1,IN_STORE,239201000002,6' HDMI Cable,2,5.99

And assume that we want to read the CSV into a List of Java POJOs for which the class looks like this:

    public class ItemOrder {
        private Instant purchaseTimestamp;
        private LocalDate purchaseDate;
        private int customerId;
        private PurchaseType type;
        private long itemNumber;
        private String name;
        private int quantity;
        private double pricePerUnit;
        
        //Getters and setters        
    }

We can easily read the CSV file into a collection of type `List<ItemOrder>` with a single line of code:

    List<ItemOrder> orders = easyCsv.read(Paths.get("path/to/orders.csv"), ItemOrder.class)

Notice that no explicit mapping needed to be defined between the columns in the file and the fields in the Java class, nor did
the column names and field names need to have the same case. The conversion happens automatically through reflection by examining
the header of the file and comparing it with the fields of the Java class. The fields are matched using relaxed binding, so the
cases between the header of the file and the Java class don't have to be the same. In this example, the file uses snake-case for the
header and camel-case for the Java class.

**Warning:** Since this reads all of the file into memory, this should only be used with small files or in non-production code.

We can also tail the CSV file with:

    List<ItemOrder> orders = easyCsv.tail(Paths.get("path/to/orders.csv", 1), ItemOrder.class)
    
In this case, `orders` will be a List of size 1 containing the last entry the file.


## Writing CSV Files
Assume that we want to write the list of orders from the previous example back out to a file again. We can simply do the following:

    easyCsv.write(file, ItemOrder.class, orders)

If we wanted the header to be formatted with a particular case, then we would do something like this:

    easyCsv.write(file, ItemOrder.class, orders, CaseFormat.LOWER_PERIOD)
    
Rows can also be appended to an existing file by supplying a `FileWriteMode.APPEND` parameter:

    easyCsv.write(file, ItemOrder.class, orders, FileWriteMode.APPEND)
    
**Warning:** Again, this requires that all the objects be loaded into memory prior to writing them to the file. This practice is not recommended for a large list of objects.

## The CaseFormat enum
The `CaseFormat` class is similar to the `CaseFormat` class in Guava, but it provides more types and the ability to get the case format type of a String.

Examples:
- `CaseFormats.getCaseFormat("my-string")` will return `CaseFormat.LOWER_HYPHEN`
- `CaseFormats.convertCase("myString", CaseFormat.UPPER_PERIOD)` will return `"MY.STRING"`

## Ranges
The `Range` implementations can be used to represent ranges of objects that implement `Comparable`.

Example:

    LocalDateTime start = LocalDateTime.parse("2018-01-01T00:00")
    LocalDateTime end = LocalDateTime.parse("2019-01-01T00:00")
    Range<LocalDateTime> dateRange = ImmutableRange.of(start, end)
    assertTrue(dateRange.includes(LocalDateTime.parse("2018-06-01T00:00")))
    
