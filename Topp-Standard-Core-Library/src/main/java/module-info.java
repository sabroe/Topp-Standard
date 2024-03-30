/**
 * Topp Standard Core addressing basics of Java SE.
 */
module com.yelstream.topp.standard.core {
    requires static lombok;
    requires org.slf4j;
    exports com.yelstream.topp.standard.lang;
    exports com.yelstream.topp.standard.time;
    exports com.yelstream.topp.standard.util.function;
}
