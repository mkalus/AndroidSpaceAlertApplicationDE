package com.boarbeard;

import java.util.HashMap;
import java.util.Map;

import com.boarbeard.audio.SoundLibrary;


    public class Settings
    {
        private Map<String, String> programSettings;

        public enum AudioSetGrammarType
        {
            English,
            German,
            Spanish, 
            Czech
        };

        private Object[] audioSetList;
        private AudioSetGrammarType[] audioSetGrammarList;
        private Map<String, Map<SoundLibrary.Elements, String>> audioSetTextTable;

        public Object[] getAudioSet()
        {
            return audioSetList;
        }
        public int getDefaultAudioSet()
        {
            if (programSettings.containsKey("DefaultAudioSet"))
            {
                for (int i = 0; i < getAudioSet().length; i++)
                {
                    if (getAudioSet().toString() == programSettings.get("DefaultAudioSet"))
                    {
                        return i;
                    }
                }
            }

            return 0;
        }
        
        public void setDefaultAudioSet(int index) {
        	programSettings.put("DefaultAudioSet", getAudioSet()[index].toString());
        }
        

        Map<SoundLibrary.Elements, String> getAudioSetText(String audioSet)
        {
            return audioSetTextTable.get(audioSet);
        }

        public AudioSetGrammarType[] getAudioGrammarSet()
        {
            return audioSetGrammarList;
        }
        public int getMissionsToGenerate()
        {
            return Integer.parseInt(programSettings.get("MissionsToGenerate"));
        }
        public void setMissionsToGenerate(int value)
        {
            programSettings.put("MissionsToGenerate", value + "");
        }
        public String getDefaultMissionFile()
        {
            return relativePath + programSettings.get("DefaultMissionFile");
        }
        public boolean getGenerateTranscript()
        {
            return Boolean.parseBoolean(programSettings.get("GenerateTranscript"));
        }
        public void setGenerateTranscript(int value)
        {
            programSettings.put("GenerateTranscript", value + "");
        }
        private String relativePath;
        public String getRelativePath()
        {
            return relativePath;
        }
        public Settings()
        {
            programSettings = new HashMap<String,String>();
            relativePath = "";
            //while(!File.Exists(relativePath + "settings.raw"))
            //{
            //    relativePath += "../";   
            //}
            
            //loadSettings(relativePath + "settings.raw");
        }
        /*
        ~Settings()
        {
            saveSettings(relativePath + "settings.raw");
        }
        */

        /*
        public void loadSettings(String file)
        {
            XmlTextReader xmlReader = new XmlTextReader(new FileStream(file,FileMode.Open,FileAccess.Read));
            Dictionary<string, string> currentSettings = null;
            string currentName = null;
            while (xmlReader.Read())
            {
                switch (xmlReader.NodeType)
                {
                    case XmlNodeType.Element:
                        if (currentSettings == null)
                        {
                            if (xmlReader.Name == "Program")
                            {
                                currentSettings = programSettings;
                            }
                            else
                            {
                                Assert.assertTrue(xmlReader.Name == "Settings");
                            }
                        }
                        else
                        {
                            Assert.assertTrue(xmlReader.Name == "Setting");
                            currentName = xmlReader.GetAttribute("name");
                        }
                        break;
                    case XmlNodeType.EndElement:
                        if (xmlReader.Name != "Setting")
                        {
                            currentSettings = null;
                        }
                        else
                        {
                            currentName = null;
                        }
                        break;
                    case XmlNodeType.Text:
                        Assert.assertTrue(currentName != null);
                        currentSettings[currentName] = xmlReader.Value;
                        break;
                }
            }
            xmlReader.Close();

            processSettings();
        }

        private void processSettings()
        {
            List audioSets = new ArrayList();
            List<AudioSetGrammarType> audioSetGrammars = new ArrayList<AudioSetGrammarType>();

            audioSetTextTable = new HashMap<String, Map<SoundLibrary.Elements,String>>();
            DirectoryInfo info = new DirectoryInfo(RelativePath + "clips/");
            foreach (DirectoryInfo folder in info.GetDirectories())
            {
                if (File.Exists(folder.FullName + "\\grammar.xml"))
                {
                    Map<SoundLibrary.Elements, String> textTable = new HashMap<SoundLibrary.Elements,String>();
                    audioSets.Add(folder.Name);
                    audioSetGrammars.Add(loadAudioSetGrammar(folder.FullName + "\\grammar.xml",textTable));
                    audioSetTextTable.put(folder.Name, textTable);
                }
                else{
                    
                }
            }

            this.audioSetList = audioSets.toArray();
            this.audioSetGrammarList = audioSetGrammars.ToArray<AudioSetGrammarType>();
            
        }

        private AudioSetGrammarType loadAudioSetGrammar(String file, Map<SoundLibrary.Elements, String> textTable)
        {
            XmlTextReader xmlReader = new XmlTextReader(new FileStream(file, FileMode.Open, FileAccess.Read));
            String currentName = null;
            AudioSetGrammarType result = AudioSetGrammarType.English;
            while (xmlReader.Read())
            {
                switch (xmlReader.NodeType)
                {
                    case XmlNodeType.Element:
                        currentName = xmlReader.Name;
                        break;
                    case XmlNodeType.Text:
                        Assert.assertTrue(currentName != null);
                        if (currentName != "Settings")
                        {
                            if (currentName == "Grammar")
                            {
                                result = (AudioSetGrammarType)Enum.Parse(typeof(AudioSetGrammarType), xmlReader.Value);
                            }
                            else
                            {
                                textTable.put(SoundLibrary.Elements.values(), xmlReader.Value);
                            }
                        }
                        break;
                }
            }
            xmlReader.Close();
            return result;
        }

        static void writeSingleSettingsBlock(XmlTextWriter textWriter, String name, Map<String, String> settings)
        {
            textWriter.WriteStartElement(name);
            for(KeyValuePair<string, string> val : settings)
            {
                textWriter.WriteStartElement("Setting");

                textWriter.WriteStartAttribute("name");
                textWriter.WriteString(val.Key);
                textWriter.WriteEndAttribute();

                textWriter.WriteString(val.Value);

                textWriter.WriteEndElement();
            }
            textWriter.WriteEndElement();
        }
        
        public void saveSettings(String file)
        {
            XmlTextWriter textWriter = new XmlTextWriter(file,null);
            textWriter.WriteStartDocument();
            textWriter.Formatting = Formatting.Indented;

            textWriter.WriteStartElement("Settings");

            writeSingleSettingsBlock(textWriter, "Program", programSettings);
            
            textWriter.WriteEndElement();
            
            textWriter.WriteEndDocument();
            textWriter.Close();
        }
        */
    }