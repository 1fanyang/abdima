# AbdIma

*AbdIma is an implementation of semantic tableaux method for Description Logics*

The proposed framework of image interpretation is implemented in two modules, 
including an image processing module to parse extraction of recognized objects in a structured file (.owl),
and an inference module of abductive reasoning based on tableau method using an ontology as prior knowledge. 

The work is completed during the PhD and PostDoc stay at Télécom ParisTech, funded by the ANR project LOGIMA and DigiCosme Post-Thèse Université Paris Saclay.

## Dependencies

* OwlApi from http://owlapi.sourceforge.net
* Pellet Reasonser from http://pellet.owldl.com

## Input Format

Both the TBox and the ABox are saved in one single OWL file. The observation is saved as one concept in the ABox.


## Usage

Example:

java -jar out/artifacts/MyReasonerAbduction.jar -k example.owl -o

-k knowledge base path

The abductive reasoning part is performed using the executable file (MyReasonerAbduction.jar) 
with a command \textit{java -jar MyReasonerAbduction.jar knowledgebase.owl observation.owl result.txt}. 


## Reference
[1] Y. Yang, J. Atif and I. Bloch, *Abductive reasoning using tableau methods for high-level image interpretation*,  KI2015
[2] Y. Yang, J. Atif, I. Bloch, *Raisonnement abductif en logique de description exploitant les domaines concrets spatiaux pour l'interprétation d'images*, RIA 2017
