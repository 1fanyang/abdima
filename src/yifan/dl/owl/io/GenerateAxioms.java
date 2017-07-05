/*
 * This class manipulates DL signatures and constructs knowledge base by creating a set of axioms.
 * This is required to be rewritten as a parser from a pattern text.
 */

package yifan.dl.owl.io;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

public class GenerateAxioms {

	/**
	 * @param args
	 */
	private File ontFile;
	private IRI ontIRI;
	private OWLOntologyManager dlmanager;
	private OWLOntology onto;
	private OWLDataFactory df;
	private DefaultPrefixManager pm;
	
	public void createKnowledge() throws OWLOntologyCreationException{
		
		onto=dlmanager.createOntology(ontIRI);
		
		
		OWLClass Brain, BrainStructure, BrainDisease, CNl, CNr, LVl, LVr, Hemisphere, PeripheralHemisphere, CentralHemisphere, Tumor, SmallDeformingTumor, 
		PeripheralTumor;
		
		Brain = df.getOWLClass("Brain", pm);
		BrainStructure = df.getOWLClass("BrainStructure", pm);
		BrainDisease = df.getOWLClass("BrainDisease", pm);
		CNl = df.getOWLClass("CNl", pm);
		CNr = df.getOWLClass("CNr", pm);
		LVl = df.getOWLClass("LVl", pm);
		LVr = df.getOWLClass("LVr", pm);
		Hemisphere = df.getOWLClass("Hemisphere", pm);
		PeripheralHemisphere = df.getOWLClass("PeripheralHemisphere", pm);
		CentralHemisphere = df.getOWLClass("CentralHemisphere", pm);
		Tumor = df.getOWLClass("Tumor", pm);
		SmallDeformingTumor = df.getOWLClass("SmallDeformingTumor", pm);
		PeripheralTumor = df.getOWLClass("PeripheralTumor", pm);		
		
		OWLObjectProperty leftOf, rightOf, above, below, infront, behind, farFrom, closeTo, isPartOf, hasPart;
		
		leftOf = df.getOWLObjectProperty("leftOf", pm);
		rightOf = df.getOWLObjectProperty("rightOf", pm);		
		above = df.getOWLObjectProperty("above", pm);
		below = df.getOWLObjectProperty("below", pm);		
		infront = df.getOWLObjectProperty("infront", pm);
		behind = df.getOWLObjectProperty("behind", pm);
		farFrom = df.getOWLObjectProperty("farFrom", pm);		
		closeTo = df.getOWLObjectProperty("closeTo", pm);
		isPartOf = df.getOWLObjectProperty("isPartOf", pm);		
		hasPart = df.getOWLObjectProperty("hasPart", pm);
		
		OWLIndividual x,y;
		x = df.getOWLNamedIndividual("x", pm);
		y = df.getOWLNamedIndividual("y", pm);
		
		
		// TBox
		Set<OWLAxiom> TBoxAxioms = new HashSet<OWLAxiom> ();
		
		TBoxAxioms.add(df.getOWLSubClassOfAxiom(Hemisphere, df.getOWLObjectSomeValuesFrom(isPartOf, Brain)));
		TBoxAxioms.add(df.getOWLSubClassOfAxiom(BrainStructure, df.getOWLObjectSomeValuesFrom(isPartOf, Brain)));
		TBoxAxioms.add(df.getOWLSubClassOfAxiom(BrainDisease, df.getOWLObjectIntersectionOf(
				df.getOWLObjectSomeValuesFrom(isPartOf, Brain),df.getOWLObjectComplementOf(BrainStructure))));
		TBoxAxioms.add(df.getOWLSubClassOfAxiom(Tumor, BrainDisease));
		TBoxAxioms.add(df.getOWLSubClassOfAxiom(LVl, df.getOWLObjectIntersectionOf(
				BrainStructure, df.getOWLObjectSomeValuesFrom(rightOf, CNl), df.getOWLObjectSomeValuesFrom(closeTo, CNl))));
		TBoxAxioms.add(df.getOWLSubClassOfAxiom(LVr, df.getOWLObjectIntersectionOf(
				BrainStructure, df.getOWLObjectSomeValuesFrom(leftOf, CNr), df.getOWLObjectSomeValuesFrom(closeTo, CNr))));
		TBoxAxioms.add(df.getOWLSubClassOfAxiom(CNl, BrainStructure));
		TBoxAxioms.add(df.getOWLSubClassOfAxiom(CNr, BrainStructure));
		TBoxAxioms.add(df.getOWLSubClassOfAxiom(PeripheralHemisphere, Hemisphere));
		TBoxAxioms.add(df.getOWLSubClassOfAxiom(CentralHemisphere, df.getOWLObjectIntersectionOf(
				Hemisphere, df.getOWLObjectComplementOf(PeripheralHemisphere))));
		TBoxAxioms.add(df.getOWLSubClassOfAxiom(PeripheralTumor, df.getOWLObjectIntersectionOf(
				Tumor, df.getOWLObjectSomeValuesFrom(isPartOf, PeripheralHemisphere), df.getOWLObjectSomeValuesFrom(farFrom, df.getOWLObjectUnionOf(LVl,LVr)))));
		TBoxAxioms.add(df.getOWLSubClassOfAxiom(LVr, df.getOWLObjectIntersectionOf(
				Tumor, df.getOWLObjectSomeValuesFrom(closeTo, df.getOWLObjectUnionOf(CNl,CNr)))));
		
		dlmanager.addAxioms(onto, TBoxAxioms);
		
		// RBox
		Set<OWLAxiom> RBoxAxioms = new HashSet<OWLAxiom>();
		RBoxAxioms.add(df.getOWLInverseObjectPropertiesAxiom(isPartOf, hasPart));
		RBoxAxioms.add(df.getOWLInverseObjectPropertiesAxiom(leftOf, rightOf));
		RBoxAxioms.add(df.getOWLInverseObjectPropertiesAxiom(above, below));
		RBoxAxioms.add(df.getOWLInverseObjectPropertiesAxiom(infront, behind));		
		RBoxAxioms.add(df.getOWLSymmetricObjectPropertyAxiom(closeTo));
		RBoxAxioms.add(df.getOWLSymmetricObjectPropertyAxiom(farFrom));
		RBoxAxioms.add(df.getOWLTransitiveObjectPropertyAxiom(hasPart));
		RBoxAxioms.add(df.getOWLTransitiveObjectPropertyAxiom(isPartOf));		
		
		dlmanager.addAxioms(onto, RBoxAxioms);
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
