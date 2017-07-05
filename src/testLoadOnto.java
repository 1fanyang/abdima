import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;


public class testLoadOnto {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// load an ontology from a file
		String filepath="./dataset/abduction_contraction/test9.owl";
		File file=new File(filepath);
		OWLOntologyManager manager= OWLManager.createOWLOntologyManager();
		try {
			manager.loadOntologyFromOntologyDocument(file);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//load an ontology from a IRI
/*	    IRI pizzaOntologyIRI = IRI.create("http://owl.cs.manchester.ac.uk/co-ode-files/ontologies/pizza.owl");
	    try {
			OWLOntology redirectedPizza = manager.loadOntology(pizzaOntologyIRI);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}