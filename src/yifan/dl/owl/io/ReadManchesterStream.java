package yifan.dl.owl.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxParserFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class ReadManchesterStream {
	public static void main(String[] args) throws OWLOntologyCreationException, IOException {
	        // Get a manager and create an empty ontology, and a parser that 
	        // can read Manchester syntax.
	        final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	        final OWLOntology ontology = manager.createOntology();
	        final OWLParser parser = new ManchesterOWLSyntaxParserFactory().createParser( manager );

	        // A small OWL ontology in the Manchester syntax.
	        final String content = "" +
	                "Prefix: so: <http://stackoverflow.com/q/21005908/1281433/>\n" +
	                "Class: so:Person\n" +
	                "Class: so:Young\n" +
	                "\n" +
	                "Class: so:Teenager\n" +
	                "  SubClassOf: (so:Person and so:Young)\n" +
	                "";

	        // Create an input stream from the ontology, and use the parser to read its 
	        // contents into the ontology.
	        
//	        try ( final InputStream in = new ByteArrayInputStream( content.getBytes() ) ) {
//	            parser.parse( new StreamDocumentSource( in ), ontology );
//	        }

	        // Iterate over the axioms of the ontology. There are more than just the subclass
	        // axiom, because the class declarations are also axioms.  All in all, there are
	        // four:  the subclass axiom and three declarations of named classes.
	        System.out.println( "== All Axioms: ==" );
	        for ( final OWLAxiom axiom : ontology.getAxioms() ) {
	            System.out.println( axiom );
	        }

	        // You can iterate over more specific axiom types, though.  For instance, 
	        // you could just iterate over the TBox axioms, in which case you'll just
	        // get the one subclass axiom. You could also iterate over
	        // ontology.getABoxAxioms() to get ABox axioms.
	        System.out.println( "== ABox Axioms: ==" );
	        for ( final OWLAxiom axiom : ontology.getTBoxAxioms( false ) ) {
	            System.out.println( axiom );
	        }
	    }
}

