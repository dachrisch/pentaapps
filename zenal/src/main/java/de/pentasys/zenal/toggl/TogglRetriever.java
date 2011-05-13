package de.pentasys.zenal.toggl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import de.pentasys.zenal.ZenalEntryList;

public class TogglRetriever {

    public ZenalEntryList readEntriesFromCsv(final String fileLocation) {
        CSVReader csvReader;
        try {
            csvReader = new CSVReader(new InputStreamReader(new FileInputStream(fileLocation), "utf-8"), ',');
        } catch (final FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        final CsvToBean<ZenalEntryMapper> csvToBean = new CsvToBean<ZenalEntryMapper>();

        final HashMap<String, String> columnToBeanMap = new HashMap<String, String>();
        columnToBeanMap.put("Start time", "from");
        columnToBeanMap.put("End time", "till");
        columnToBeanMap.put("Description", "description");
        columnToBeanMap.put("Project", "project");

        final HeaderColumnNameTranslateMappingStrategy<ZenalEntryMapper> togglCsvToZenalMapper = new HeaderColumnNameTranslateMappingStrategy<ZenalEntryMapper>();
        togglCsvToZenalMapper.setColumnMapping(columnToBeanMap);
        togglCsvToZenalMapper.setType(ZenalEntryMapper.class);
        final List<ZenalEntryMapper> zenalMapperEntries = csvToBean.parse(togglCsvToZenalMapper, csvReader);

        final ZenalEntryList zenalEntries = new ZenalEntryList();
        for (final ZenalEntryMapper zenalEntryMapper : zenalMapperEntries) {
            zenalEntries.add(zenalEntryMapper.toZenalEntry());
        }
        return zenalEntries;
    }
}
