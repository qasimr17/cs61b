import edu.princeton.cs.algs4.In;
import main.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1991);
        expectedYears.add(1992);
        expectedYears.add(1994);
        expectedYears.add(1995);

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>();
        expectedTotal.add(0.0);
        expectedTotal.add(100.0);
        expectedTotal.add(600.0);
        expectedTotal.add(500.0);

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void testRestrictedMap() {
        TimeSeries basicTreeMap = new TimeSeries();

        basicTreeMap.put(1400, 9.3);
        basicTreeMap.put(1997, 18.0);
        basicTreeMap.put(2001, 21.0);
        basicTreeMap.put(2030, 67.0);

        TimeSeries restrictedMap = new TimeSeries(basicTreeMap, 1800, 2002);

        List<Integer> restrictedYears = new ArrayList<>();
        restrictedYears.add(1997);
        restrictedYears.add(2001);

        assertThat(restrictedMap.years()).isEqualTo(restrictedYears);

        List<Double> restrictedValues = new ArrayList<>();
        restrictedValues.add(18.0);
        restrictedValues.add(21.0);

        assertThat(restrictedMap.data()).isEqualTo(restrictedValues);

    }

    @Test
    public void avoidDuplicateEntries() {

        TimeSeries testSeries = new TimeSeries();

        testSeries.put(1, 2.0);
        testSeries.put(1, 2.0);

        List<Integer> nonDuplicateYears = new ArrayList<>();
        nonDuplicateYears.add(1);

        assertThat(testSeries.years()).isEqualTo(nonDuplicateYears);

        List<Double> nonDuplicateValues = new ArrayList<>();
        nonDuplicateValues.add(2.0);

        assertThat(testSeries.data()).isEqualTo(nonDuplicateValues);

    }

    @Test
    public void addTimeSeries() {

        TimeSeries seriesA = new TimeSeries();
        TimeSeries seriesB = new TimeSeries();
        TimeSeries seriesAPlusB = new TimeSeries();

        seriesA.put(1, 2.0);
        seriesA.put(2, 3.0);
        seriesA.put(3, 4.0);
        seriesA.put(4, 5.0);

        seriesB.put(3, 5.0);
        seriesB.put(4, 6.0);
        seriesB.put(5, 100.0);

        seriesAPlusB = seriesA.plus(seriesB);

        assertThat(seriesAPlusB.get(1)).isEqualTo(2.0);
        assertThat(seriesAPlusB.get(2)).isEqualTo(3.0);
        assertThat(seriesAPlusB.get(3)).isEqualTo(9.0);
        assertThat(seriesAPlusB.get(4)).isEqualTo(11.0);
        assertThat(seriesAPlusB.get(5)).isEqualTo(100.0);
    }

    @Test
    public void testDividedBy() {
        TimeSeries numerator = new TimeSeries();
        TimeSeries denominator = new TimeSeries();

        numerator.put(2000, 10.0);
        numerator.put(2001, 20.0);
        numerator.put(2002, 30.0);

        denominator.put(2000, 2.0);
        denominator.put(2001, 4.0);
        denominator.put(2002, 5.0);

        TimeSeries result = numerator.dividedBy(denominator);

        assertThat(result.get(2000)).isWithin(1E-10).of(5.0);
        assertThat(result.get(2001)).isWithin(1E-10).of(5.0);
        assertThat(result.get(2002)).isWithin(1E-10).of(6.0);
    }

    @Test
    public void testDividedByMissingYearThrowsException() {
        TimeSeries numerator = new TimeSeries();
        TimeSeries denominator = new TimeSeries();

        numerator.put(2000, 10.0);
        numerator.put(2001, 20.0);

        denominator.put(2000, 2.0);

        assertThrows(IllegalArgumentException.class, () -> {
            numerator.dividedBy(denominator);
        });
    }

    @Test
    public void testDividedByIgnoresExtraYears() {
        TimeSeries numerator = new TimeSeries();
        TimeSeries denominator = new TimeSeries();

        numerator.put(2000, 10.0);
        numerator.put(2001, 20.0);

        denominator.put(2000, 2.0);
        denominator.put(2001, 4.0);
        denominator.put(2002, 100.0);

        TimeSeries result = numerator.dividedBy(denominator);

        assertThat(result.years())
                .containsExactly(2000, 2001)
                .inOrder();

        assertThat(result.get(2000)).isWithin(1E-10).of(5.0);
        assertThat(result.get(2001)).isWithin(1E-10).of(5.0);
    }

    @Test
    public void testRestrictedMapIncludesBoundaries() {
        TimeSeries series = new TimeSeries();

        series.put(2000, 10.0);
        series.put(2001, 20.0);
        series.put(2002, 30.0);

        TimeSeries restricted = new TimeSeries(series, 2000, 2002);

        assertThat(restricted.years())
                .containsExactly(2000, 2001, 2002)
                .inOrder();

        assertThat(restricted.data())
                .containsExactly(10.0, 20.0, 30.0)
                .inOrder();
    }

    @Test
    public void testPlusDoesNotCreateMissingYears() {
        TimeSeries seriesA = new TimeSeries();
        TimeSeries seriesB = new TimeSeries();

        seriesA.put(2000, 10.0);
        seriesA.put(2002, 20.0);

        seriesB.put(2001, 5.0);
        seriesB.put(2002, 7.0);

        TimeSeries result = seriesA.plus(seriesB);

        assertThat(result.years())
                .containsExactly(2000, 2001, 2002)
                .inOrder();

        assertThat(result.get(2000)).isEqualTo(10.0);
        assertThat(result.get(2001)).isEqualTo(5.0);
        assertThat(result.get(2002)).isEqualTo(27.0);
    }
} 