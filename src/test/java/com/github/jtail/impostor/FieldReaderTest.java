package com.github.jtail.impostor;

import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Column;

/**
 *
 */
public class FieldReaderTest {
    private final static Annotator<Chicken> imp = Annotator.create(Chicken.class);
    private final static Column onWing = imp.annotate(imp.stub().getWing(), Column.class);
    private final static Column onHead = imp.annotate(imp.stub().getHead(), Column.class);

    @Test
    public void annotationPresentOnHead() {
        Assert.assertEquals("nobrain", onHead.name());
    }

    @Test
    public void annotationAbsentOnWing() {
        Assert.assertNull(onWing);
    }



}
