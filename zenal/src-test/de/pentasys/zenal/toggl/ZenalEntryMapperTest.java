package de.pentasys.zenal.toggl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.joda.time.DateTime;
import org.junit.Test;

import de.pentasys.zenal.builder.Category;
import de.pentasys.zenal.builder.Project;

public class ZenalEntryMapperTest {
    @Test
    public void parseDateFromString() throws Exception {
        final ZenalEntryMapper mapper = new ZenalEntryMapper();
        mapper.setFrom("12/20/2010 12:13");
        mapper.setTill("11/21/2011 13:15");

        assertThat(mapper.toZenalEntry().getFrom(), is(new DateTime(2010, 12, 20, 12, 13, 0, 0)));
        assertThat(mapper.toZenalEntry().getTill(), is(new DateTime(2011, 11, 21, 13, 15, 0, 0)));
    }

    @Test
    public void setProject() throws Exception {
        final ZenalEntryMapper mapper = new ZenalEntryMapper();

        assertThat(mapper.toZenalEntry().getProjectId(), nullValue());

        mapper.setProject("mediasaturn");
        assertThat(mapper.toZenalEntry().getProjectId(), is(Project.MEDIASATURN.getProjectId()));
    }

    @Test
    public void setDescriptionAndParseCategory() throws Exception {
        final ZenalEntryMapper mapper = new ZenalEntryMapper();
        mapper.setDescription("anything");
        assertThat(mapper.toZenalEntry().getCategory(), is(Category.PROJECT.getCategory()));

        mapper.setDescription("anfahrt");
        assertThat(mapper.toZenalEntry().getCategory(), is(Category.TRAVEL_START.getCategory()));

        mapper.setDescription("anyanfahrtthing");
        assertThat(mapper.toZenalEntry().getCategory(), is(Category.TRAVEL_START.getCategory()));

        mapper.setDescription("rückfahrt");
        assertThat(mapper.toZenalEntry().getCategory(), is(Category.TRAVEL_END.getCategory()));

        mapper.setDescription("anyrückfahrtthing");
        assertThat(mapper.toZenalEntry().getCategory(), is(Category.TRAVEL_END.getCategory()));

        mapper.setDescription("rueckfahrt");
        assertThat(mapper.toZenalEntry().getCategory(), is(Category.TRAVEL_END.getCategory()));

        mapper.setDescription("anyrueckfahrtthing");
        assertThat(mapper.toZenalEntry().getCategory(), is(Category.TRAVEL_END.getCategory()));
    }
}
