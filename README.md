# Projet Simulation Hopital

## Guide d'utilisation

Le fichier **Projet_Simulation_Hopital-Crochemore_Soulier.jar** permet d'executer le code avec une grande interface (application par defaut). Si celle-ci est trop grande, essayer avec **Projet_Simulation_Hopital-Crochemore_Soulier-Petite_Interface.jar**.

Pour lancer les fichiers, taper la commande : 
java -jar Projet_Simulation_Hopital-Crochemore_Soulier.jar

Les champs textes doivent être valider avec entrée. Si un champs n'a pas été validé, il sera marqué en rouge, il suffira de le modifier (ou de relancer) pour que la simulation s'éxecute.

Le dossier **fichiers/** est important pour assurer le bon fonctionnement de ces fichiers executables.

## Code Source

Le code a été fait avec Eclipse et NetBeans (seulement pour les interfaces), il se trouve  dans **Projet**. Le projet peut donc être lancer directement depuis eclipse, plusieurs librairies doivent alors être ajoutées au projet :

- **swing-time-picker.jar** et **TimingFramework-0.55.jar** : utilent pour les timePicker
- tous les fichiers .jar dans **/jfreechart-1.0.19/lib/** : utilent pour generer les graphiques

Il faut aussi specifier le chemin vers un jdk.

Si d'autres problemes surviennent, il se peut que vous essayez d'utiliser un IDE different de Eclipse, l'IDE que nous avons utilise pour ce projet.

L'ensemble des fichiers qui peuvent paraitre _bizarres_ sont des fichiers utiles pour ouvrir le projet avec NetBeans.

## Fichiers de données

Lors de la simulation, les données générées sont extraites dans différents fichiers, ceux-ci peuvent être trouvées dans le dossier **fichiers/** :
 - le fichier **fichiers/extraction.json** contient les données affichées dans les graphes
 - le dossier **fichiers/historique_plannings** contient l'ensemble des plannings générées tout au long de la journée. Ceux-ci sont générées chaque fois des nouvelles informations sont connues par l'hopital, ils correspondent au plan d'action de l'hopital. Ainsi, un planning est générée chaque fois qu'un patient urgent est déclaré.


## Specifications

Vous trouverez dans le dossier **Astah** l'ensemble des diagramme de classe que nous avons generes au fur et a mesure du projet. Il s'agit de fichier devant etre ouvert avec Astah. 

Vous trouverez aussi deux fichiers .jpg correspondant respectivement au premier et dernier diagramme du projet.

## JavaDoc

Dans le dossier **JavaDoc** se trouve ce qui a ete genere par JavaDoc pour notre projet. Ouvrir **index.html** sur un navigateur pour la visualiser.

##

_Crochemore Arthur (Sheerven) et Soulier Louis (Hatraxe)_