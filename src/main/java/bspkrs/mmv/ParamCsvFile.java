/*
 * Copyright (C) 2014 bspkrs
 * Portions Copyright (C) 2014 Alex "immibis" Campbell
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package bspkrs.mmv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ParamCsvFile
{
    private final File                      file;
    private final Map<String, ParamCsvData> srgParamName2ParamCsvData;
    private boolean                         isDirty;
    private String                          headerLine;

    public ParamCsvFile(File file) throws IOException
    {
        this.file = file;
        srgParamName2ParamCsvData = new TreeMap<String, ParamCsvData>();
        readFromFile();
        isDirty = false;
    }

    public void readFromFile() throws IOException
    {
        Scanner in = new Scanner(new BufferedReader(new FileReader(file)));
        try
        {
            in.useDelimiter(",");
            headerLine = in.nextLine(); // Skip header row
            while (in.hasNextLine())
            {
                String srgName = in.next();
                String mcpName = in.next();
                String side = in.nextLine().substring(1);
                srgParamName2ParamCsvData.put(srgName, new ParamCsvData(srgName, mcpName, Integer.valueOf(side)));
            }
        }
        finally
        {
            in.close();
        }
    }

    public void writeToFile() throws IOException
    {
        if (isDirty)
        {
            if (file.exists())
            {
                File fileBak = new File(file.getAbsolutePath() + "_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".bak");
                file.renameTo(fileBak);
            }

            file.createNewFile();

            PrintWriter out = new PrintWriter(new FileWriter(file));
            out.println(headerLine);

            for (ParamCsvData data : srgParamName2ParamCsvData.values())
                out.println(data.toCsv());

            out.close();

            isDirty = false;
        }
    }

    public boolean hasCsvDataForKey(String srgName)
    {
        return srgParamName2ParamCsvData.containsKey(srgName);
    }

    public ParamCsvData getCsvDataForKey(String srgName)
    {
        return srgParamName2ParamCsvData.get(srgName);
    }

    public void updateCsvDataForKey(String srgName, ParamCsvData csvData)
    {
        srgParamName2ParamCsvData.put(srgName, csvData);
        isDirty = true;
    }

    public boolean isDirty()
    {
        return isDirty;
    }

    public void setIsDirty(boolean bol)
    {
        isDirty = bol;
    }
}
