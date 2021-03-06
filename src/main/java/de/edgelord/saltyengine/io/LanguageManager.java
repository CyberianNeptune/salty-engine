/*
 * This software was published under the MIT License.
 * The full LICENSE file can be found here: https://github.com/edgelord314/salty-enigne/tree/master/LICENSE
 *
 * Copyright (c) since 2018 by the Salty Engine developers,
 * Maintained by Malte Dostal
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package de.edgelord.saltyengine.io;

import de.edgelord.saltyengine.resource.Resource;
import de.edgelord.stdf.reading.DataReader;

import java.io.IOException;

/**
 * This class is a set of static methods to let your game support multiple languages.
 * The text is saved in a stdf file. A simple example for the text "It's dangerous to go alone!", saved with the name "old_man_0"
 * and "Take this with you!", save with the name "old_man_1"
 * (excuse my bad french!)
 * <pre>
 *     {@code
 *
 *     {old_man_0}
 *     The text for the old man to be introduced
 *     (english)It's*_*dangerous*_*to*_*go*_*alone!(*english)
 *     (german)Es*_*ist*_*gefährlich*_*alleine*_*zu*_*gehen!(*german)
 *     (french)C'est*_*dangereux*_*d'aller*_*seul!(*french)
 *     {*old_man_0}
 *
 *     {old_man_1}
 *     The text for the old man to turn out nice
 *     (english)Take*_*this*_*with*_*you!(*english)
 *     (german)Nimm*_*dies*_*mit!(*german)
 *     (french)I*_*DON'T*_*KNOW*_*HOW*_*TO*_*SAY*_*THAT*_*IN*_*FRENCH!(*french)
 *     {*old_man_1}
 *     }
 * </pre>
 * <p>
 * You have to encode any spaces (" ") with "*_*" and you can leave any comments in between tags, like you can see in the example.
 * You can use any languages, you just have to make sure that {@link #language} is one the ones you used.
 */
public class LanguageManager {

    /**
     * The language to use. This can be any, but it has to be used in the text file as well
     */
    private static String language = "english";

    private static DataReader textReader = null;

    /**
     * Initializes the mechanisms to read language specific text out of a file with the given name relative to
     * the given resource.
     * The given name should point to a stdf file. See <a href="https://www.github.com/edgelord314/stdf">the repository</a> or
     * the example above for more information.
     *
     * @param fileName the name of the text-resource file, without any extensions even though the extension of the file must
     *                 be {@link DataReader#SDB_FILE_EXTENSION}, which is ".sdb"
     * @param resource the resource to locate the file
     * @throws IOException when the I/O process fails
     */
    public static void init(String fileName, Resource resource) throws IOException {
        textReader = new DataReader(resource.getFileResource(fileName + DataReader.SDB_FILE_EXTENSION));
    }

    /**
     * Reads the text with the given name in the language with the name {@link #language} from the file specified
     * by the {@link #init(String, Resource)} call. For an example please look above to the class description.
     *
     * @param textId the id of the text
     * @return the text with the given id and the language with the name {@link #language}
     */
    public static String getText(String textId) {
        if (textReader == null) {
            System.err.println("Cannot read text while LanguageManager wasn't initialized. Call LanguageManager.init(String, Resource) before!");
            return "";
        } else {
            return textReader.getSpecies(textId).getTagValue(language);
        }
    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        LanguageManager.language = language;
    }
}
