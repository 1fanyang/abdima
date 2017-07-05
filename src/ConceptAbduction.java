/**
 * Created by yifan on 2017/3/15.
 */

import yifan.dl.owl.reasoner.SvReasoner;
import java.io.File;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import org.apache.commons.cli.*;

public class ConceptAbduction {
    // default setting variables
    static String kbPath = "./dataset/example.owl";
    static String obsPath = "./dataset/testObservation.owl";
    static String outPath = "./dataset/output.txt";

    public static void main(String []args) throws Exception{

			// command line argument parsing
			Options options = new Options();

			Option knowledge = new Option("k", "knowledge", true, "input knowledge file path");
			knowledge.setRequired(true);
			options.addOption(knowledge);

			Option observation = new Option("a", "abduction", true, "input abduction observation file path");
			observation.setRequired(true);
			options.addOption(observation);

			Option output = new Option("o", "output", true, "output file");
			output.setRequired(true);
			options.addOption(output);

			Option examples = new Option("d", "default", true, "default setting" );
			examples.setRequired(false);
			options.addOption(examples);

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

			String knowledgePath = kbPath;
			String observationFilePath = obsPath;
			String outputFilePath = cmd.getOptionValue("output");

			if(cmd.hasOption("d")){
				System.out.println("default settings...");
			}else{
                knowledgePath = cmd.getOptionValue("knowledge");
				observationFilePath = cmd.getOptionValue("abduction");
				outputFilePath = cmd.getOptionValue("output");
			}

			System.out.println(knowledgePath);
			System.out.println(observationFilePath);
			System.out.println(outputFilePath);

			// parsing TBox knowledge base.
            OWLOntologyManager manager=OWLManager.createOWLOntologyManager();

            File file = new File(knowledgePath);
            try{

                OWLOntology knowledgeOntology = manager.loadOntologyFromOntologyDocument(file);


                // init reasoner
                SvReasoner reasoner = new SvReasoner(knowledgeOntology);


                //Init tableau T(x) F(x)
                reasoner.testInit(knowledgeOntology);

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
