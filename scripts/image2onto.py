import numpy as np
from PIL import Image

from skimage.color import rgb2gray
from skimage.morphology import binary_closing

from scipy import ndimage
import matplotlib.pyplot as plt

import sys, os

import io

def write_header(file):
    file.write("<?xml version=\".0\"?> \n")
    #file.write("rdf:RDF")
    #file.write(" < !DOCTYP Ontology[ <!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" >] >")

    file.write("< !DOCTYPE Ontology[ \n")
    file.write("    <!ENTITY xsd    \"http://www.w3.org/2001/XMLSchema#\" >\n")
    file.write("    <!ENTITY xml    \"http://www.w3.org/XML/1998/namespace\" >\n")
    file.write("    <!ENTITY rdfs    \"http://www.w3.org/2000/01/rdf-schema#\" >\n")
    file.write("    <!ENTITY rdf    \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" >\n ")


    file.write("<Ontology xmlns = \http://www.w3.org/2002/07/owl#\" \n")
    file.write("    xmlns = \"http://www.w3.org/2002/07/owl#\" \n")
    file.write("    xml:base = \"http://www.semanticweb.org/yiyang/ontologies/2017/5/observation\" \n")
    file.write("    xmlns:rdfs = \"http://www.w3.org/2000/01/rdf-schema#\" \n")
    file.write("    xmlns:xsd = \"http://www.w3.org/2001/XMLSchema#\" \n")
    file.write("    xmlns:rdf = \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" \n")
    file.write("    xmlns:xml = \"http://www.w3.org/XML/1998/namespace\" \n")
    file.write("    ontologyIRI = \"http://www.semanticweb.org/yiyang/ontologies/2017/5/observation\" \n")

    file.write("    <Prefix name=\"\" IRI =\"http://www.w3.org/2002/07/owl#\" \n")
    file.write("    <Prefix name=\"owl\" IRI =\"http://www.w3.org/2002/07/owl#\" \n")
    file.write("    <Prefix name=\"rdf\" IRI =\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" \n")
    file.write("    <Prefix name=\"xsd\" IRI =\"http://www.w3.org/2001/XMLSchema#\" \n")
    file.write("    <Prefix name=\"rdfs\" IRI =\"http://www.w3.org/2000/01/rdf-schema#\" \n")

def write_concept(concept, file):
    file.write("< Declaration \n <Class IRI=\"#%s\" /> \n </Declaration>\n" % (concept))

def write_role(role, file):
    file.write("< Declaration \n <ObjectProperty IRI=\"#%s\" /> \n </Declaration>\n" % (role))

def write_individual(individual, file):
    file.write("< Declaration \n <NamedIndividual IRI=\"#%s\" /> \n </Declaration>\n" %(individual))

def write_classAssertion(concept, individual, file):
    file.write("< ClassAssertion \n <Class IRI=\"%s\"> \n <NamedIndividual IRI=\"#%s\" /> \n </ClassAssertion>\n" % (concept, individual))

def write_comment(individual, comment,file):
    file.write("<AnnotationAssertion> \n <AnnotationProperty abbreviatedIRI=\"dfs:comment\"/> \n <IRI>#%s</IRI> \n <Literal> datatypeIRI=\"&rdf;PlainLiteral\"> %s  </Literal>\n" % (individual, comment))

def write_footer(file):
    file.write("</Ontology>")

def test():
    f = open("observation.owl", "w")
    write_header(f)
    write_concept("A", f)
    write_concept("B", f)
    write_individual("x", f)
    write_individual("y", f)
    write_classAssertion("A", "x", f)
    write_classAssertion("B", "y", f)
    write_comment("x", "comment", f)
    write_footer(f)
    f.close()

  
"""
transform a segmented image to labeled objects

brain  graylevel > 0
tumor  graylevel = 226
leftLV graylevel = 
"""
img = Image.open("gray_2.png")
pix = np.array(img, dtype=np.uint8)
# grey = rgb2gray(img)

filename = "observation_image.owl"
f = open(filename,"w")
write_header(f)

brain = np.copy(pix)
brain[ brain > 0 ] = 255
#plt.imshow(brain)
out = Image.fromarray(brain)
out.save("brain.png")
write_concept("Brain", f)
write_classAssertion("Brain", "brain", f)
write_comment("brain", "brain.png", f)

tumor = np.copy(pix)
tumor[ tumor == 226 ] = 255
tumor[ tumor != 255 ] = 0
out = Image.fromarray(tumor)
out.save("tumor.png")
write_concept("Tumor", f)
write_classAssertion("Tumor", "tumor", f)
write_comment("tumor", "tumor.png", f)

llv = np.copy(pix)
llv[ llv == 166 ] = 255
llv[ llv != 255 ] = 0
out = Image.fromarray(llv)
out.save("llv.png")
write_concept("LeftLateralVentricle", f)
write_classAssertion("LeftLateralVentricle", "llv", f)
write_comment("llv", "llv.png", f)

rlv = np.copy(pix)
rlv[ rlv == 110 ] = 255
rlv[ rlv != 255 ] = 0
out = Image.fromarray(rlv)
out.save("rlv.png")
write_concept("RightLateralVentricle", f)
write_classAssertion("RightLateralVentricle", "rlv", f)
write_comment("rlv", "rlv.png", f)

lcn = np.copy(pix)
lcn[ lcn == 105 ] = 255
lcn[ lcn != 255 ] = 0
lcn_close = np.copy(lcn)
out = Image.fromarray(lcn_close)
out.save("lcn.png")
write_concept("LeftCaudNucleus", f)
write_classAssertion("LeftCaudNucleus", "lcn", f)
write_comment("lcn", "lcn.png", f)

rcn = np.copy(pix)
rcn[ rcn == 109 ] = 255
rcn[ rcn != 255 ] = 0
#plt.imshow(rcn)
out = Image.fromarray(rcn)
out.save("rcn.png")
write_concept("RightCaudNucleus", f)
write_classAssertion("RightCaudNucleus", "rcn", f)
write_comment("rcn", "rcn.png", f)

lpu = np.copy(pix)
lpu[ lpu == 127 ] = 255
lpu[ lpu != 255 ] = 0
#plt.imshow(lpu)
out = Image.fromarray(lpu)
out.save("lpu.png")
write_concept("LeftPutamen", f)
write_classAssertion("LeftPutamen", "lpu", f)
write_comment("lpu", "lpu.png", f)

rpu = np.copy(pix)
rpu[ rpu == 29 ] = 255
rpu[ rpu != 255 ] = 0
# plt.imshow(rpu)
out = Image.fromarray(rpu)
out.save("rpu.png")
write_concept("RightPutamen", f)
write_classAssertion("RightPutamen", "rpu", f)
write_comment("rpu", "rpu.png", f)

lth = np.copy(pix)
lth[ lth == 150 ] = 255
lth[ lth != 255 ] = 0
#plt.imshow(lth)
out = Image.fromarray(lth)
out.save("lth.png")
write_concept("LeftThalamus", f)
write_classAssertion("LeftThalamus", "lth", f)
write_comment("lth", "lth.png", f)

rth = np.copy(pix)
rth[ rth == 89 ] = 255
rth[ rth != 255 ] = 0
#plt.imshow(rth)
out = Image.fromarray(rth)
out.save("rth.png")
write_concept("RightThalamus", f)
write_classAssertion("RightThalamus", "rth", f)
write_comment("rth", "rth.png", f)


write_footer(f)
f.close()