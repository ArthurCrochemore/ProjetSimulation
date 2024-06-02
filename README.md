# Projet Simulation Hopital

## Objectif

L'objectif de ce projet était de développer un modèle de simulation à événement discret sur un modèle choisi. Nous avons choisi de représenter la répartition des salles d'opérations d'un hôpital.

Pour plus de détails, vous pouvez consulter le rapport ici : https://docs.google.com/document/d/1EDGARHhCo8SgqxnvzbSIp-IZV1chDh5p5DkGzwETCZs/edit

## Guide d'utilisation

Le fichier **Projet_Simulation_Hopital-Crochemore_Soulier.jar** permet d'exécuter le code avec une grande interface (application par défaut). Si celle-ci est trop grande, essayer avec **Projet_Simulation_Hopital-Crochemore_Soulier-Petite_Interface.jar**.

Pour lancer les fichiers, tapez la commande : 
> java -jar Projet_Simulation_Hopital-Crochemore_Soulier.jar

Les champs textes doivent être validés avec entrée. Si un champ n'a pas été validé, il sera marqué en rouge, il suffira de le modifier (ou de relancer) pour que la simulation s'exécute.

Le dossier **fichiers/** est important pour assurer le bon fonctionnement de ces fichiers exécutables.

## Code Source

Le code a été fait avec Eclipse et NetBeans (seulement pour les interfaces), il se trouve  dans **/Projet**. Le projet peut donc être lancé directement depuis Eclipse, plusieurs librairies doivent alors être ajoutées au projet :

- **swing-time-picker.jar** et **TimingFramework-0.55.jar** : utilent pour les timePicker
- tous les fichiers .jar dans **/jfreechart-1.0.19/lib/** : utilent pour générer les graphiques

Il faut aussi spécifier le chemin vers un jdk.

Si d'autres problèmes surviennent, il se peut que vous essayez d'utiliser un IDE différent de Eclipse, l'IDE que nous avons utilisé pour ce projet.

L'ensemble des fichiers qui peuvent paraître _bizarres_ sont des fichiers utiles pour ouvrir le projet avec NetBeans.

## Fichiers de données

Lors de la simulation, les données générées sont extraites dans différents fichiers, ceux-ci peuvent être trouvées dans le dossier **fichiers/** :
 - le fichier **fichiers/extraction.json** contient les données affichées dans les graphes
 - le dossier **fichiers/historique_plannings** contient l'ensemble des plannings générées tout au long de la journée. Ceux-ci sont générés chaque fois des nouvelles informations sont connues par l'hôpital, ils correspondent au plan d'action de l'hôpital. Ainsi, un planning est généré chaque fois qu'un patient urgent est déclaré.


## Specifications

Vous trouverez dans le dossier **Astah** l'ensemble des diagrammes de classe que nous avons générés au fur et à mesure du projet. Il s'agit de fichier devant être ouvert avec Astah. 

Vous trouverez aussi deux fichiers .jpg correspondant respectivement au premier et dernier diagramme du projet.

## JavaDoc

Dans le dossier **JavaDoc** se trouve ce qui a été généré par JavaDoc pour notre projet. Ouvrir **index.html** sur un navigateur pour la visualiser.

##

_Arthur Crochemore et Louis Soulier_