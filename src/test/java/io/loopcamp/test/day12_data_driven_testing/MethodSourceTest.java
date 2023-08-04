package io.loopcamp.test.day12_data_driven_testing;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;

public class MethodSourceTest {

    public static List<String> getCountries(){

        List <String> countries = Arrays.asList("Canada", "Portugal", "Germany", "France");
        return countries;

    }

    @ParameterizedTest
    @MethodSource("getCountries")
    public void countriesTest(String eachCountry){

        System.out.println("Each country: "+eachCountry);
        System.out.println("Each length: "+eachCountry.length());

    }
}
