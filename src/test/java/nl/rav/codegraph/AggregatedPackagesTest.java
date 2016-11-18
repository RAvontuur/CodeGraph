package nl.rav.codegraph;

import jdepend.framework.JavaPackage;
import nl.rav.codegraph.service.AggregatedPackages;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by rene on 16-3-16.
 */
public class AggregatedPackagesTest {

    @Test
    public void testZeroPackages() throws IOException {
        Collection jPackages = new ArrayList();

        AggregatedPackages p = new AggregatedPackages(jPackages);
        p.aggregate("com.test", new String[]{"com.test"});

        assertThat(p.getPackages().size(), is(0));
        assertThat(p.getArrows().size(), is(0));
    }

    @Test
    public void testOnePackage() throws IOException {
        JavaPackage j0 = new JavaPackage("com.test.web");

        Collection jPackages = Arrays.asList(j0);
        AggregatedPackages p = new AggregatedPackages(jPackages);
        p.aggregate("com.test", new String[]{"com.test"});

        assertThat(p.getPackages().size(), is(1));
        assertThat(p.getArrows().size(), is(0));
        assertThat(p.getPackages().get("com.test.web").toString(), is("0,0,web"));
    }

    @Test
    public void testOnePackageSameLevel() throws IOException {
        JavaPackage j0 = new JavaPackage("com.test.web");

        Collection jPackages = Arrays.asList(j0);
        AggregatedPackages p = new AggregatedPackages(jPackages);
        p.aggregate("com.test.web", new String[]{"com.test.web"});

        assertThat(p.getPackages().size(), is(1));
        assertThat(p.getArrows().size(), is(0));
        assertThat(p.getPackages().get("com.test.web").toString(), is("0,0,."));
    }

    @Test
    public void testOnePackageOutsideScope() throws IOException {
        JavaPackage j0 = new JavaPackage("com.test.web");

        Collection jPackages = Arrays.asList(j0);
        AggregatedPackages p = new AggregatedPackages(jPackages);
        p.aggregate("com.lib.tool", new String[]{"com.test.web"});

        assertThat(p.getPackages().size(), is(0));
        assertThat(p.getArrows().size(), is(0));
    }

    @Test
    public void testTwoPackagesOneArrow() throws IOException {
        JavaPackage j0 = new JavaPackage("com.test.web");
        JavaPackage j1 = new JavaPackage("com.test.data");

        j0.addEfferent(j1);
        j1.addAfferent(j0);

        Collection jPackages = Arrays.asList(j0, j1);
        AggregatedPackages p = new AggregatedPackages(jPackages);
        p.aggregate("com.test", new String[]{"com.test"});

        assertThat(p.getPackages().size(), is(2));
        assertThat(p.getArrows().size(), is(1));
        assertThat(p.getPackages().get("com.test.web").toString(), is("0,0,web"));
        assertThat(p.getPackages().get("com.test.data").toString(), is("0,1,data"));
        assertThat(p.getArrows().get(0).toString(), is("0,1,0,0"));
    }

    @Test
    public void testTwoPackagesTwoArrows() throws IOException {
        JavaPackage j0 = new JavaPackage("com.test.web");
        JavaPackage j1 = new JavaPackage("com.test.data");

        j0.addEfferent(j1);
        j1.addEfferent(j0);
        j1.addAfferent(j0);
        j0.addAfferent(j1);

        Collection jPackages = Arrays.asList(j0, j1);
        AggregatedPackages p = new AggregatedPackages(jPackages);
        p.aggregate("com.test", new String[]{"com.test"});

        assertThat(p.getPackages().size(), is(2));
        assertThat(p.getArrows().size(), is(2));
        assertThat(p.getPackages().get("com.test.web").toString(), is("0,0,web"));
        assertThat(p.getPackages().get("com.test.data").toString(), is("0,1,data"));
        assertThat(p.getArrows().get(0).toString(), is("0,0,0,1"));
        assertThat(p.getArrows().get(1).toString(), is("0,1,0,0"));
    }

    @Test
    public void testTwoPackagesAggregated() throws IOException {
        JavaPackage j0 = new JavaPackage("com.test.web");
        JavaPackage j1 = new JavaPackage("com.test.web.home");

        j0.addEfferent(j1);
        j1.addAfferent(j0);

        Collection jPackages = Arrays.asList(j0, j1);
        AggregatedPackages p = new AggregatedPackages(jPackages);
        p.aggregate("com.test", new String[]{"com.test"});

        assertThat(p.getPackages().size(), is(1));
        assertThat(p.getArrows().size(), is(0));
        assertThat(p.getPackages().get("com.test.web").toString(), is("0,0,web"));
    }

    @Test
    public void testThreePackagesTwoCyclicOneIndependent() throws IOException {
        JavaPackage j0 = new JavaPackage("com.test.web");
        JavaPackage j1 = new JavaPackage("com.test.data");
        JavaPackage j2 = new JavaPackage("com.test.loner");

        j0.addEfferent(j1);
        j1.addEfferent(j0);
        j1.addAfferent(j0);
        j0.addAfferent(j1);

        Collection jPackages = Arrays.asList(j0, j1, j2);
        AggregatedPackages p = new AggregatedPackages(jPackages);
        p.aggregate("com.test", new String[]{"com.test"});

        assertThat(p.getPackages().size(), is(3));
        assertThat(p.getArrows().size(), is(2));
        assertThat(p.getPackages().get("com.test.loner").toString(), is("1,0,loner"));
        assertThat(p.getPackages().get("com.test.web").toString(), is("0,0,web"));
        assertThat(p.getPackages().get("com.test.data").toString(), is("0,1,data"));
        assertThat(p.getArrows().get(0).toString(), is("0,0,0,1"));
        assertThat(p.getArrows().get(1).toString(), is("0,1,0,0"));
    }
}