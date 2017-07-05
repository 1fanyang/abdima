import org.apache.commons.cli.*;
import yifan.dl.owl.reasoner.SvReasoner;


import java.io.File;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;


public class TestAbduction {

	    // ontology path
		static String ONTO_PATH = "./dataset/example.owl";
		
		static OWLOntology onto = null;

		static SvReasoner reasoner = null;
		
		public static void main(String args[]){

			OWLOntologyManager manager=OWLManager.createOWLOntologyManager();
			Options options = new Options();

			Option knowledge = new Option("k", "knowledge", true, "input knowledge file path");
			knowledge.setRequired(true);
			options.addOption(knowledge);

			CommandLineParser parser = new DefaultParser();
			HelpFormatter formatter = new HelpFormatter();
			CommandLine cmd;
			try {
				cmd = parser.parse(options, args);
			} catch (ParseException e) {
				System.out.println(e.getMessage());
				formatter.printHelp("utility-name", options);

				System.exit(1);
				return;
			}

			String file_path= cmd.getOptionValue("knowledge");
			File file = new File(file_path);
			try{
				onto = manager.loadOntologyFromOntologyDocument(file);


				// init reasoner
				reasoner = new SvReasoner(onto);
						
				reasoner.testInit(onto);

		        // start new abduction by construct a new tableau tree
				// and get an hypothesis of explanation
		        reasoner.nvAbduction();

		        System.out.println("=========================================");
		        reasoner.PrintTableauxSet();
		        System.out.println("=========================================");
		        
					
			}catch(Exception e){
				e.printStackTrace();
			}
		}
}