/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fr.univtours.polytech.vue;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import com.orsoncharts.util.json.JSONArray;
import com.orsoncharts.util.json.JSONObject;
import com.orsoncharts.util.json.parser.JSONParser;
import com.raven.event.EventTimePicker;

import fr.univtours.polytech.MainSimulation;
import fr.univtours.polytech.util.Tuple;

// ============ INITIALISATION ========================================================================

/**
 *
 * @author Arthur
 */
public class Saisie extends javax.swing.JFrame {

	private Integer regle1;
	private Integer regle2;
	private Integer regle3;

	// heures de la journee
	private LocalTime heureDebutJournee;
	private LocalTime heureFinJournee;

	// Nb de Ressource
	private Integer nbInfirmiere;
	private Integer nbChirurgien;
	private Integer nbSallesPeuEquipee;
	private Integer nbSallesSemiEquipee;
	private Integer nbSallesTresEquipee;
	private Integer nbSallesReserveesUrgence;

	// Constantes
	private Integer tempsPreparation;
	private Integer tempsAnesthesie;
	private Integer tempsLiberation;
	private Integer moyTempsOperation;
	private Integer marge;

	// Data pour les Patients
	private Integer nbPatientsRDVPE;
	private Integer nbPatientsRDVSE;
	private Integer nbPatientsRDVTE;
	private Integer nbPatientsUrgent;
	private Map<Integer, List<LocalTime>> map;

	/**
	 * Creates new form Saisie
	 */
	public Saisie() {
		regle1 = 1;
		regle2 = 1;
		regle3 = 1;

		heureDebutJournee = LocalTime.of(8, 0, 0, 0);
		heureFinJournee = LocalTime.of(18, 0, 0, 0);

		nbInfirmiere = -1;
		nbChirurgien = -1;
		nbSallesPeuEquipee = -1;
		nbSallesSemiEquipee = -1;
		nbSallesTresEquipee = -1;
		nbSallesReserveesUrgence = -1;

		tempsPreparation = -1;
		tempsAnesthesie = -1;
		tempsLiberation = -1;
		moyTempsOperation = -1;
		marge = -1;

		nbPatientsRDVPE = 0;
		nbPatientsRDVSE = 0;
		nbPatientsRDVTE = 0;
		nbPatientsUrgent = 0;
		map = new HashMap<Integer, List<LocalTime>>();

		initComponents();
		panelChartTools.setVisible(false);
		pickerHeureDebut.set24hourMode(true);
		pickerHeureFin.set24hourMode(true);

		pickerHeureDebut.addEventTimePicker(new EventTimePicker() {
			@Override
			public void timeSelected(String string) {
				heureDebutJournee = getHeure(pickerHeureDebut);
				btnHeureDebut.setText(heureDebutJournee.toString());

				nbPatientsRDVPE = 0;
				nbPatientsRDVSE = 0;
				nbPatientsRDVTE = 0;
				nbPatientsUrgent = 0;
				map = new HashMap<Integer, List<LocalTime>>();
			}
		});
		heureDebutJournee = LocalTime.of(8, 0);
		btnHeureDebut.setText(heureDebutJournee.toString());
		pickerHeureDebut.setSelectedTime(Date
				.from((Instant) heureDebutJournee.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant()));
		pickerHeureFin.addEventTimePicker(new EventTimePicker() {
			@Override
			public void timeSelected(String string) {
				heureFinJournee = getHeure(pickerHeureFin);
				btnHeureFin.setText(heureFinJournee.toString());

				nbPatientsRDVPE = 0;
				nbPatientsRDVSE = 0;
				nbPatientsRDVTE = 0;
				nbPatientsUrgent = 0;
				map = new HashMap<Integer, List<LocalTime>>();
			}
		});
		heureFinJournee = LocalTime.of(18, 0);
		btnHeureFin.setText(heureFinJournee.toString());
		pickerHeureFin.setSelectedTime(Date
				.from((Instant) heureFinJournee.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant()));
	}

	private LocalTime getHeure(com.raven.swing.TimePicker picker) {
		String str = picker.getSelectedTime();
		char[] dest = new char[4];
		str.getChars(0, 2, dest, 0);
		str.getChars(3, 5, dest, 2);

		int i1 = (((int) dest[0]) - 48) * 10 + (((int) dest[1]) - 48);
		int i2 = (((int) dest[2]) - 48) * 10 + (((int) dest[3]) - 48);
		return LocalTime.of(i1, i2);
	}

	public void setNbPatientsRDVPE(Integer nbPatientsRDVPE) {
		this.nbPatientsRDVPE = nbPatientsRDVPE;
	}

	public void setNbPatientsRDVSE(Integer nbPatientsRDVSE) {
		this.nbPatientsRDVSE = nbPatientsRDVSE;
	}

	public void setNbPatientsRDVTE(Integer nbPatientsRDVTE) {
		this.nbPatientsRDVTE = nbPatientsRDVTE;
	}

	public void setNbPatientsUrgent(Integer nbPatientsUrgent) {
		this.nbPatientsUrgent = nbPatientsUrgent;
	}

	public void setMap(Map<Integer, List<LocalTime>> map) {
		this.map = map;
	}

	public LocalTime getHeureDebutJournee() {
		return heureDebutJournee;
	}

	public LocalTime getHeureFinJournee() {
		return heureFinJournee;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// <editor-fold defaultstate="collapsed" desc="Generated
	// <editor-fold defaultstate="collapsed" desc="Generated
	// <editor-fold defaultstate="collapsed" desc="Generated
	// <editor-fold defaultstate="collapsed" desc="Generated
	// <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pickerHeureDebut = new com.raven.swing.TimePicker();
        pickerHeureFin = new com.raven.swing.TimePicker();
        panelSaisie = new javax.swing.JPanel();
        txtDebut = new javax.swing.JLabel();
        btnHeureDebut = new javax.swing.JButton();
        txtFin = new javax.swing.JLabel();
        btnHeureFin = new javax.swing.JButton();
        titleHorraires = new javax.swing.JLabel();
        titleRegleG = new javax.swing.JLabel();
        txtRegleInf = new javax.swing.JLabel();
        stringRegleGestion1 = new javax.swing.JComboBox<>();
        txtRegleSalle = new javax.swing.JLabel();
        stringRegleGestion2 = new javax.swing.JComboBox<>();
        txtRegleChir = new javax.swing.JLabel();
        stringRegleGestion3 = new javax.swing.JComboBox<>();
        txtNbSalles = new javax.swing.JLabel();
        valueNbSalleReserve = new javax.swing.JTextField();
        titleConstantes = new javax.swing.JLabel();
        txtPrepa = new javax.swing.JLabel();
        valueTempsPrepa = new javax.swing.JTextField();
        txtAnesthesie = new javax.swing.JLabel();
        valueTempsAnes = new javax.swing.JTextField();
        txtLibe = new javax.swing.JLabel();
        valueTempsLiber = new javax.swing.JTextField();
        titlePatients = new javax.swing.JLabel();
        btnOpenSaisiePatient = new javax.swing.JButton();
        btnSimulation = new javax.swing.JButton();
        titleQteRessources = new javax.swing.JLabel();
        txtNbInf = new javax.swing.JLabel();
        valueNbInfirmier = new javax.swing.JTextField();
        txtNbChir = new javax.swing.JLabel();
        valueNbChirurgien = new javax.swing.JTextField();
        txtPE = new javax.swing.JLabel();
        valueNbSallePE = new javax.swing.JTextField();
        txtSE = new javax.swing.JLabel();
        valueNbSalleSE = new javax.swing.JTextField();
        txtTE = new javax.swing.JLabel();
        valueNbSalleTE = new javax.swing.JTextField();
        txtMoyOpe = new javax.swing.JLabel();
        valueMoyTempsOpe = new javax.swing.JTextField();
        txtMarge = new javax.swing.JLabel();
        valueMarge = new javax.swing.JTextField();
        panelChart = new javax.swing.JPanel();
        labelLancez = new javax.swing.JLabel();
        labelImage = new javax.swing.JLabel();
        panelChartTools = new javax.swing.JPanel();
        btnChartInfirmier = new javax.swing.JButton();
        btnChartChirugien = new javax.swing.JButton();
        btnChartSalle = new javax.swing.JButton();
        chkBoxPE = new javax.swing.JCheckBox();
        chkBoxSE = new javax.swing.JCheckBox();
        chkBoxTE = new javax.swing.JCheckBox();
        btnChartPatient = new javax.swing.JButton();
        chkBoxRDV = new javax.swing.JCheckBox();
        chkBoxUrgent = new javax.swing.JCheckBox();
        txtTitreGraph = new javax.swing.JLabel();

        pickerHeureDebut.setForeground(new java.awt.Color(236, 213, 129));

        pickerHeureFin.setForeground(new java.awt.Color(236, 213, 129));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFocusCycleRoot(false);
        setMinimumSize(new java.awt.Dimension(1280, 720));

        panelSaisie.setBackground(new java.awt.Color(236, 213, 129));
        panelSaisie.setMinimumSize(new java.awt.Dimension(300, 720));
        panelSaisie.setPreferredSize(new java.awt.Dimension(400, 975));

        txtDebut.setText("Heure début simulation :");

        btnHeureDebut.setBackground(new java.awt.Color(236, 213, 129));
        btnHeureDebut.setText("8:00");
        btnHeureDebut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHeureDebutActionPerformed(evt);
            }
        });

        txtFin.setText("Heure fin simulation :");

        btnHeureFin.setBackground(new java.awt.Color(236, 213, 129));
        btnHeureFin.setText("18:00");
        btnHeureFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHeureFinActionPerformed(evt);
            }
        });

        titleHorraires.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        titleHorraires.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleHorraires.setText("Horraires");
        titleHorraires.setPreferredSize(new java.awt.Dimension(400, 20));

        titleRegleG.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        titleRegleG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleRegleG.setText("Règles de Gestion");
        titleRegleG.setPreferredSize(new java.awt.Dimension(400, 20));

        txtRegleInf.setText("Gestion Priorité Infirmiers :");

        stringRegleGestion1.setBackground(new java.awt.Color(236, 213, 129));
        stringRegleGestion1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Priorité préparation des salles", "Priorité libération des salles", "Première en attente", "Priorité aux Urgences" }));
        stringRegleGestion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stringRegleGestion1ActionPerformed(evt);
            }
        });

        txtRegleSalle.setText("Gestion Attribution des salles :");

        stringRegleGestion2.setBackground(new java.awt.Color(236, 213, 129));
        stringRegleGestion2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Premier en attente", "Priorité rendez-vous", "Priorité urgence", "Premier en attente, réservation dynamique" }));
        stringRegleGestion2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stringRegleGestion2ActionPerformed(evt);
            }
        });

        txtRegleChir.setText("Gestion Priorité Chirurgiens :");

        stringRegleGestion3.setBackground(new java.awt.Color(236, 213, 129));
        stringRegleGestion3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dans l'ordre des attentes", "Priorite aux salles d'urgence" }));
        stringRegleGestion3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stringRegleGestion3ActionPerformed(evt);
            }
        });

        txtNbSalles.setText("Nombre Salle Réservées  :");

        valueNbSalleReserve.setBackground(new java.awt.Color(236, 213, 129));
        valueNbSalleReserve.setText("0");
        valueNbSalleReserve.setPreferredSize(new java.awt.Dimension(70, 22));
        valueNbSalleReserve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueNbSalleReserveActionPerformed(evt);
            }
        });

        titleConstantes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        titleConstantes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleConstantes.setText("Constantes");
        titleConstantes.setPreferredSize(new java.awt.Dimension(400, 20));

        txtPrepa.setText("Temps Prépartion des Salles (en min) :");

        valueTempsPrepa.setBackground(new java.awt.Color(236, 213, 129));
        valueTempsPrepa.setText("0");
        valueTempsPrepa.setPreferredSize(new java.awt.Dimension(70, 22));
        valueTempsPrepa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueTempsPrepaActionPerformed(evt);
            }
        });

        txtAnesthesie.setText("Temps Anesthésie des Patients (en min) :");

        valueTempsAnes.setBackground(new java.awt.Color(236, 213, 129));
        valueTempsAnes.setText("0");
        valueTempsAnes.setPreferredSize(new java.awt.Dimension(70, 22));
        valueTempsAnes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueTempsAnesActionPerformed(evt);
            }
        });

        txtLibe.setText("Temps Libération des Salles (en min) :");

        valueTempsLiber.setBackground(new java.awt.Color(236, 213, 129));
        valueTempsLiber.setText("0");
        valueTempsLiber.setPreferredSize(new java.awt.Dimension(70, 22));
        valueTempsLiber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueTempsLiberActionPerformed(evt);
            }
        });

        titlePatients.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        titlePatients.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titlePatients.setText("Saisie Patients");
        titlePatients.setPreferredSize(new java.awt.Dimension(400, 20));

        btnOpenSaisiePatient.setBackground(new java.awt.Color(236, 213, 129));
        btnOpenSaisiePatient.setText("Ouvrir Fenetre de Saisie");
        btnOpenSaisiePatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenSaisiePatientActionPerformed(evt);
            }
        });

        btnSimulation.setBackground(new java.awt.Color(236, 213, 129));
        btnSimulation.setText("Lancer Simulation");
        btnSimulation.setPreferredSize(new java.awt.Dimension(188, 24));
        btnSimulation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimulationActionPerformed(evt);
            }
        });

        titleQteRessources.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        titleQteRessources.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleQteRessources.setText("Quantité de Ressources");
        titleQteRessources.setPreferredSize(new java.awt.Dimension(400, 20));

        txtNbInf.setText("Nombre Infirmier :");

        valueNbInfirmier.setBackground(new java.awt.Color(236, 213, 129));
        valueNbInfirmier.setText("0");
        valueNbInfirmier.setPreferredSize(new java.awt.Dimension(70, 22));
        valueNbInfirmier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueNbInfirmierActionPerformed(evt);
            }
        });

        txtNbChir.setText("Nombre Chirurgien :");

        valueNbChirurgien.setBackground(new java.awt.Color(236, 213, 129));
        valueNbChirurgien.setText("0");
        valueNbChirurgien.setPreferredSize(new java.awt.Dimension(70, 22));
        valueNbChirurgien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueNbChirurgienActionPerformed(evt);
            }
        });

        txtPE.setText("Nombre Salle Peu équipée :");

        valueNbSallePE.setBackground(new java.awt.Color(236, 213, 129));
        valueNbSallePE.setText("0");
        valueNbSallePE.setPreferredSize(new java.awt.Dimension(70, 22));
        valueNbSallePE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueNbSallePEActionPerformed(evt);
            }
        });

        txtSE.setText("Nombre Salle Semi équipée :");

        valueNbSalleSE.setBackground(new java.awt.Color(236, 213, 129));
        valueNbSalleSE.setText("0");
        valueNbSalleSE.setPreferredSize(new java.awt.Dimension(70, 22));
        valueNbSalleSE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueNbSalleSEActionPerformed(evt);
            }
        });

        txtTE.setText("Nombre Salle Très équipée :");

        valueNbSalleTE.setBackground(new java.awt.Color(236, 213, 129));
        valueNbSalleTE.setText("0");
        valueNbSalleTE.setPreferredSize(new java.awt.Dimension(70, 22));
        valueNbSalleTE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueNbSalleTEActionPerformed(evt);
            }
        });

        txtMoyOpe.setText("Moyenne des temps d'Operation (en min) :");

        valueMoyTempsOpe.setBackground(new java.awt.Color(236, 213, 129));
        valueMoyTempsOpe.setText("0");
        valueMoyTempsOpe.setPreferredSize(new java.awt.Dimension(70, 22));
        valueMoyTempsOpe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueMoyTempsOpeActionPerformed(evt);
            }
        });

        txtMarge.setText("Marge pour le temps Moyen (en %) :");

        valueMarge.setBackground(new java.awt.Color(236, 213, 129));
        valueMarge.setText("0");
        valueMarge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueMargeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSaisieLayout = new javax.swing.GroupLayout(panelSaisie);
        panelSaisie.setLayout(panelSaisieLayout);
        panelSaisieLayout.setHorizontalGroup(
            panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSaisieLayout.createSequentialGroup()
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSaisieLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNbChir)
                            .addComponent(txtPE)
                            .addComponent(txtNbInf)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSaisieLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSE, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTE, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNbSalles, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(valueNbChirurgien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(valueNbInfirmier, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(valueNbSallePE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(valueNbSalleSE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(valueNbSalleTE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(valueNbSalleReserve, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57))
            .addGroup(panelSaisieLayout.createSequentialGroup()
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSaisieLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRegleSalle)
                            .addComponent(txtRegleInf)
                            .addComponent(txtRegleChir)))
                    .addGroup(panelSaisieLayout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSimulation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOpenSaisiePatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addComponent(titleHorraires, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(titleRegleG, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(titleConstantes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelSaisieLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titlePatients, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSaisieLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(stringRegleGestion3, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stringRegleGestion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stringRegleGestion1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
            .addGroup(panelSaisieLayout.createSequentialGroup()
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSaisieLayout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtFin)
                            .addComponent(txtDebut))
                        .addGap(30, 30, 30)
                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnHeureDebut)
                            .addComponent(btnHeureFin)))
                    .addGroup(panelSaisieLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtLibe)
                            .addComponent(txtAnesthesie)
                            .addComponent(txtMoyOpe)
                            .addComponent(txtMarge)
                            .addComponent(txtPrepa))
                        .addGap(18, 18, 18)
                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(valueTempsPrepa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(valueTempsAnes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(valueTempsLiber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(valueMoyTempsOpe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(valueMarge, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(titleQteRessources, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelSaisieLayout.setVerticalGroup(
            panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSaisieLayout.createSequentialGroup()
                .addComponent(titleHorraires, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHeureDebut)
                    .addComponent(txtDebut))
                .addGap(18, 18, 18)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFin)
                    .addComponent(btnHeureFin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(titleRegleG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtRegleInf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stringRegleGestion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtRegleSalle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stringRegleGestion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtRegleChir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stringRegleGestion3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(titleConstantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrepa)
                    .addComponent(valueTempsPrepa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(valueTempsAnes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAnesthesie))
                .addGap(18, 18, 18)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLibe)
                    .addComponent(valueTempsLiber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMoyOpe)
                    .addComponent(valueMoyTempsOpe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMarge)
                    .addComponent(valueMarge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(titleQteRessources, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNbInf)
                    .addComponent(valueNbInfirmier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNbChir)
                    .addComponent(valueNbChirurgien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPE)
                    .addComponent(valueNbSallePE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSE)
                    .addComponent(valueNbSalleSE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTE)
                    .addComponent(valueNbSalleTE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNbSalles)
                    .addComponent(valueNbSalleReserve, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(titlePatients, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnOpenSaisiePatient)
                .addGap(18, 18, 18)
                .addComponent(btnSimulation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        getContentPane().add(panelSaisie, java.awt.BorderLayout.CENTER);

        panelChart.setBackground(new java.awt.Color(255, 255, 255));
        panelChart.setForeground(new java.awt.Color(255, 255, 255));
        panelChart.setMinimumSize(new java.awt.Dimension(980, 720));
        panelChart.setPreferredSize(new java.awt.Dimension(1370, 975));

        labelLancez.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelLancez.setForeground(new java.awt.Color(51, 51, 51));
        labelLancez.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelLancez.setText("Lancez une simulation pour voir vos resultats s'afficher ici");
        labelLancez.setPreferredSize(new java.awt.Dimension(1480, 25));

        labelImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fr/univtours/polytech/vue/image.jpg"))); // NOI18N
        labelImage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        panelChartTools.setBackground(new java.awt.Color(153, 153, 153));

        btnChartInfirmier.setText("Attente Infirmiers");
        btnChartInfirmier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChartInfirmierActionPerformed(evt);
            }
        });

        btnChartChirugien.setText("Attente Chirurgiens");
        btnChartChirugien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChartChirugienActionPerformed(evt);
            }
        });

        btnChartSalle.setText("Attente Salles");
        btnChartSalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChartSalleActionPerformed(evt);
            }
        });

        chkBoxPE.setText("Peu équipées");

        chkBoxSE.setText("Semi équipées");

        chkBoxTE.setText("Très équipées");

        btnChartPatient.setText("Attente Patients");
        btnChartPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChartPatientActionPerformed(evt);
            }
        });

        chkBoxRDV.setText("RDV");

        chkBoxUrgent.setText("Urgent");

        javax.swing.GroupLayout panelChartToolsLayout = new javax.swing.GroupLayout(panelChartTools);
        panelChartTools.setLayout(panelChartToolsLayout);
        panelChartToolsLayout.setHorizontalGroup(
            panelChartToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChartToolsLayout.createSequentialGroup()
                .addGroup(panelChartToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelChartToolsLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(panelChartToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnChartInfirmier, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnChartChirugien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(btnChartSalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelChartToolsLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(panelChartToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkBoxPE)
                            .addComponent(chkBoxTE)
                            .addComponent(chkBoxSE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelChartToolsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelChartToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnChartPatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelChartToolsLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(panelChartToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chkBoxUrgent)
                                    .addComponent(chkBoxRDV))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        panelChartToolsLayout.setVerticalGroup(
            panelChartToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChartToolsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnChartInfirmier)
                .addGap(12, 12, 12)
                .addComponent(btnChartChirugien)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnChartSalle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkBoxPE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkBoxSE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkBoxTE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChartPatient)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkBoxRDV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkBoxUrgent)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtTitreGraph.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        txtTitreGraph.setForeground(new java.awt.Color(255, 255, 255));
        txtTitreGraph.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTitreGraph.setText("_");
        txtTitreGraph.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelChartLayout = new javax.swing.GroupLayout(panelChart);
        panelChart.setLayout(panelChartLayout);
        panelChartLayout.setHorizontalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChartLayout.createSequentialGroup()
                .addContainerGap(160, Short.MAX_VALUE)
                .addGroup(panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChartLayout.createSequentialGroup()
                        .addComponent(txtTitreGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(157, 157, 157))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChartLayout.createSequentialGroup()
                        .addComponent(labelImage)
                        .addGap(133, 133, 133)))
                .addComponent(panelChartTools, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelChartLayout.createSequentialGroup()
                .addGap(211, 211, 211)
                .addComponent(labelLancez, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelChartLayout.setVerticalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChartLayout.createSequentialGroup()
                .addGroup(panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelChartLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtTitreGraph)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 888, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelLancez, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelChartLayout.createSequentialGroup()
                        .addComponent(panelChartTools, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        getContentPane().add(panelChart, java.awt.BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private Integer recupererValeurLue(javax.swing.JTextField textField) {
		String str = textField.getText();
		Integer valeur = 0;
		try {
			valeur = Integer.parseInt(str);
			if (valeur < 0) {
				throw new NumberFormatException();
			}
			textField.setForeground(new Color(0, 200, 0));
		} catch (NumberFormatException e) {
			textField.setText("0");
			textField.setForeground(new Color(255, 0, 0));
			return -1;
		}
		return valeur;
	}

	private void valueNbInfirmierActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_valueNbInfirmierActionPerformed
		// TODO add your handling code here:
		nbInfirmiere = recupererValeurLue(valueNbInfirmier);
	}// GEN-LAST:event_valueNbInfirmierActionPerformed

	private void valueNbChirurgienActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_valueNbChirurgienActionPerformed
		// TODO add your handling code here:
		nbChirurgien = recupererValeurLue(valueNbChirurgien);
	}// GEN-LAST:event_valueNbChirurgienActionPerformed

	private void valueNbSallePEActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_valueNbSallePEActionPerformed
		// TODO add your handling code here:
		nbSallesPeuEquipee = recupererValeurLue(valueNbSallePE);
	}// GEN-LAST:event_valueNbSallePEActionPerformed

	private void valueNbSalleSEActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_valueNbSalleSEActionPerformed
		// TODO add your handling code here:
		nbSallesSemiEquipee = recupererValeurLue(valueNbSalleSE);
	}// GEN-LAST:event_valueNbSalleSEActionPerformed

	private void valueNbSalleTEActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_valueNbSalleTEActionPerformed
		// TODO add your handling code here:
		nbSallesTresEquipee = recupererValeurLue(valueNbSalleTE);
		if (!valueNbSalleReserve.getText().equals("0")) {
			valueNbSalleReserve.setText("0");
			valueNbSalleReserve.setForeground(new Color(255, 0, 0));

			nbSallesReserveesUrgence = -1;
		}
	}// GEN-LAST:event_valueNbSalleTEActionPerformed

	private void btnOpenSaisiePatientActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnOpenSaisiePatientActionPerformed
		// Ouvre la fentre SaisiePatient:

		SaisiePatient fenetreSaisiePatient = new SaisiePatient(this, nbPatientsRDVPE, nbPatientsRDVSE, nbPatientsRDVTE,
				nbPatientsUrgent, map);
		fenetreSaisiePatient.setLocationRelativeTo(null);
		fenetreSaisiePatient.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		fenetreSaisiePatient.setVisible(true);
	}// GEN-LAST:event_btnOpenSaisiePatientActionPerformed

	private void valueTempsPrepaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_valueTempsPrepaActionPerformed
		// TODO add your handling code here:
		tempsPreparation = recupererValeurLue(valueTempsPrepa);
	}// GEN-LAST:event_valueTempsPrepaActionPerformed

	private void valueTempsAnesActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_valueTempsAnesActionPerformed
		// TODO add your handling code here:
		tempsAnesthesie = recupererValeurLue(valueTempsAnes);
	}// GEN-LAST:event_valueTempsAnesActionPerformed

	private void valueTempsLiberActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_valueTempsLiberActionPerformed
		// TODO add your handling code here:
		tempsLiberation = recupererValeurLue(valueTempsLiber);
	}// GEN-LAST:event_valueTempsLiberActionPerformed

	private void btnHeureDebutActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHeureDebutActionPerformed
		// TODO add your handling code here:
		pickerHeureDebut.showPopup(this, 350, 0);
	}// GEN-LAST:event_btnHeureDebutActionPerformed

	private void btnHeureFinActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHeureFinActionPerformed
		// TODO add your handling code here:
		pickerHeureFin.showPopup(this, 350, 30);
	}// GEN-LAST:event_btnHeureFinActionPerformed

	private void valueNbSalleReserveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_valueNbSalleReserveActionPerformed
		// TODO add your handling code here:
		int i = recupererValeurLue(valueNbSalleReserve);

		if (i <= nbSallesTresEquipee) {
			nbSallesReserveesUrgence = i;
		} else {
			valueNbSalleReserve.setText("0");
			valueNbSalleReserve.setForeground(new Color(255, 0, 0));
		}
	}// GEN-LAST:event_valueNbSalleReserveActionPerformed

	private void stringRegleGestion3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_stringRegleGestion3ActionPerformed
		// TODO add your handling code here:
		String str = stringRegleGestion3.getSelectedItem().toString();

		if (str.equals("Dans l'ordre des attentes")) {
			regle3 = 1;
		} else {
			regle3 = 2;
		}
	}// GEN-LAST:event_stringRegleGestion3ActionPerformed

	private void stringRegleGestion1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_stringRegleGestion1ActionPerformed
		// TODO add your handling code here:
		String str = stringRegleGestion1.getSelectedItem().toString();

		if (str.equals("Priorite aux salles en attente de preparation")) {
			regle1 = 1;
		} else {
			if (str.equals("Priorite aux salles en attente de liberation")) {
				regle1 = 2;
			} else {
				if (str.equals("Dans l'ordre des attentes")) {
					regle1 = 3;
				} else {
					regle1 = 4;
				}
			}
		}
	}// GEN-LAST:event_stringRegleGestion1ActionPerformed

	private void stringRegleGestion2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_stringRegleGestion2ActionPerformed
		// TODO add your handling code here:
		String str = stringRegleGestion2.getSelectedItem().toString();

		if (str.equals("Priorite aux urgences")) {
			regle2 = 1;
		} else {
			if (str.equals("Priorite aux rendez-vous")) {
				regle2 = 2;
			} else {
				regle2 = 3;
			}
		}
	}// GEN-LAST:event_stringRegleGestion2ActionPerformed

	private void valueMoyTempsOpeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_valueMoyTempsOpeActionPerformed
		// TODO add your handling code here:
		moyTempsOperation = recupererValeurLue(valueMoyTempsOpe);
	}// GEN-LAST:event_valueMoyTempsOpeActionPerformed

	private void valueMargeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_valueMargeActionPerformed
		// TODO add your handling code here:
		marge = recupererValeurLue(valueMarge);
	}// GEN-LAST:event_valueMargeActionPerformed

	private void btnSimulationActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSimulationActionPerformed
		// TODO add your handling code here:
		try {
			int pottNbInfirmiere = recupererValeurLue(valueNbInfirmier);
			int pottNbChirurgien = recupererValeurLue(valueNbChirurgien);
			int pottNbSallesPE = recupererValeurLue(valueNbSallePE);
			int pottNbSallesSE = recupererValeurLue(valueNbSalleSE);
			int pottNbSallesTE = recupererValeurLue(valueNbSalleTE);
			int pottNbSallesReservees = recupererValeurLue(valueNbSalleReserve);
			int pottTempsPrepa = recupererValeurLue(valueTempsPrepa);
			int pottTempsAnest = recupererValeurLue(valueTempsAnes);
			int pottTempsLiber = recupererValeurLue(valueTempsLiber);
			int pottTempsOpe = recupererValeurLue(valueMoyTempsOpe);
			int pottMarge = recupererValeurLue(valueMarge);

			int[] valeurs = { nbInfirmiere, nbChirurgien, nbSallesPeuEquipee, nbSallesSemiEquipee, nbSallesTresEquipee,
					nbSallesReserveesUrgence, tempsPreparation, tempsAnesthesie, tempsLiberation, moyTempsOperation,
					marge };

			JTextField[] champsSaisie = { valueNbInfirmier, valueNbChirurgien, valueNbSallePE, valueNbSalleSE,
					valueNbSalleTE, valueNbSalleReserve, valueTempsPrepa, valueTempsAnes, valueTempsLiber,
					valueMoyTempsOpe, valueMarge };

			boolean valeursToutesValides = true;

			if (nbInfirmiere != pottNbInfirmiere) {
				nbInfirmiere = pottNbInfirmiere;
			}
			if (nbChirurgien != pottNbChirurgien) {
				nbChirurgien = pottNbChirurgien;
			}
			if (nbSallesPeuEquipee != pottNbSallesPE) {
				nbSallesPeuEquipee = pottNbSallesPE;
			}

			if (nbSallesSemiEquipee != pottNbSallesSE) {
				nbSallesSemiEquipee = pottNbSallesSE;
			}

			if (nbSallesTresEquipee != pottNbSallesTE) {
				nbSallesTresEquipee = pottNbSallesTE;
			}

			if (nbSallesReserveesUrgence != pottNbSallesReservees) {
				nbSallesReserveesUrgence = pottNbSallesReservees;
			}

			if (tempsPreparation != pottTempsPrepa) {
				tempsPreparation = pottTempsPrepa;
			}

			if (tempsAnesthesie != pottTempsAnest) {
				tempsAnesthesie = pottTempsAnest;
			}

			if (tempsLiberation != pottTempsLiber) {
				tempsLiberation = pottTempsLiber;
			}

			if (moyTempsOperation != pottTempsOpe) {
				moyTempsOperation = pottTempsOpe;
			}

			if (marge != pottMarge) {
				marge = pottMarge;
			}

			for (int i = 0; i < valeurs.length; i++) {
				/* Si une valeur est invalide (-1, renvoie erreur de recupererValeurLue) */
				if (valeurs[i] < 0) {
					valeursToutesValides = false;
					champsSaisie[i].setText("0");
					champsSaisie[i].setForeground(new Color(255, 0, 0));
				}
			}

			/* Si toutes les valeurs sont bonnes, on lance la simulation */
			if (valeursToutesValides) {
				extraireDonnee();
				MainSimulation.main(null);
				initialiserGraph();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// GEN-LAST:event_btnSimulationActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void ouvrir(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Saisie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Saisie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Saisie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Saisie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Saisie().setVisible(true);
			}
		});

	}

	private void extraireDonnee() {
		try {
			PrintWriter writer = new PrintWriter("../fichier.txt", "UTF-8");

			writer.println(regle1);
			writer.println(regle2);
			writer.println(regle3);
			writer.println(heureDebutJournee.toString());
			writer.println(heureFinJournee.toString());
			writer.println(nbInfirmiere);
			writer.println(nbChirurgien);

			LocalTime heurePreparation = LocalTime.of((tempsPreparation / 60) % 24, tempsPreparation % 60);
			writer.println(heurePreparation.toString());
			LocalTime heureAnesthesie = LocalTime.of((tempsAnesthesie / 60) % 24, tempsAnesthesie % 60);
			writer.println(heureAnesthesie.toString());
			LocalTime heureLiberation = LocalTime.of((tempsLiberation / 60) % 24, tempsLiberation % 60);
			writer.println(heureLiberation.toString());
			writer.println(moyTempsOperation);
			writer.println(marge);
			writer.println(nbSallesPeuEquipee);
			writer.println(nbSallesSemiEquipee);
			writer.println(nbSallesTresEquipee);
			writer.println(nbSallesReserveesUrgence);

			writer.println(nbPatientsRDVPE);
			writer.println(nbPatientsRDVSE);
			writer.println(nbPatientsRDVTE);
			writer.println(nbPatientsUrgent);

			for (Integer indice = 0; indice < nbPatientsRDVTE + nbPatientsRDVSE + nbPatientsRDVPE; indice++) {
				writer.println(map.get(indice).get(0));
				writer.println(map.get(indice).get(1));
			}

			for (Integer indice = nbPatientsRDVTE + nbPatientsRDVSE + nbPatientsRDVPE; indice < nbPatientsUrgent
					+ nbPatientsRDVTE + nbPatientsRDVSE + nbPatientsRDVPE; indice++) {
				writer.println(map.get(indice).get(0));
				writer.println(map.get(indice).get(1));
				writer.println(map.get(indice).get(2));
			}

			writer.close();
		} catch (FileNotFoundException e) {
			System.err.println(e);
		} catch (UnsupportedEncodingException e) {
			System.err.println(e);
		}
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChartChirugien;
    private javax.swing.JButton btnChartInfirmier;
    private javax.swing.JButton btnChartPatient;
    private javax.swing.JButton btnChartSalle;
    private javax.swing.JButton btnHeureDebut;
    private javax.swing.JButton btnHeureFin;
    private javax.swing.JButton btnOpenSaisiePatient;
    private javax.swing.JButton btnSimulation;
    private javax.swing.JCheckBox chkBoxPE;
    private javax.swing.JCheckBox chkBoxRDV;
    private javax.swing.JCheckBox chkBoxSE;
    private javax.swing.JCheckBox chkBoxTE;
    private javax.swing.JCheckBox chkBoxUrgent;
    private javax.swing.JLabel labelImage;
    private javax.swing.JLabel labelLancez;
    private javax.swing.JPanel panelChart;
    private javax.swing.JPanel panelChartTools;
    private javax.swing.JPanel panelSaisie;
    private com.raven.swing.TimePicker pickerHeureDebut;
    private com.raven.swing.TimePicker pickerHeureFin;
    private javax.swing.JComboBox<String> stringRegleGestion1;
    private javax.swing.JComboBox<String> stringRegleGestion2;
    private javax.swing.JComboBox<String> stringRegleGestion3;
    private javax.swing.JLabel titleConstantes;
    private javax.swing.JLabel titleHorraires;
    private javax.swing.JLabel titlePatients;
    private javax.swing.JLabel titleQteRessources;
    private javax.swing.JLabel titleRegleG;
    private javax.swing.JLabel txtAnesthesie;
    private javax.swing.JLabel txtDebut;
    private javax.swing.JLabel txtFin;
    private javax.swing.JLabel txtLibe;
    private javax.swing.JLabel txtMarge;
    private javax.swing.JLabel txtMoyOpe;
    private javax.swing.JLabel txtNbChir;
    private javax.swing.JLabel txtNbInf;
    private javax.swing.JLabel txtNbSalles;
    private javax.swing.JLabel txtPE;
    private javax.swing.JLabel txtPrepa;
    private javax.swing.JLabel txtRegleChir;
    private javax.swing.JLabel txtRegleInf;
    private javax.swing.JLabel txtRegleSalle;
    private javax.swing.JLabel txtSE;
    private javax.swing.JLabel txtTE;
    private javax.swing.JLabel txtTitreGraph;
    private javax.swing.JTextField valueMarge;
    private javax.swing.JTextField valueMoyTempsOpe;
    private javax.swing.JTextField valueNbChirurgien;
    private javax.swing.JTextField valueNbInfirmier;
    private javax.swing.JTextField valueNbSallePE;
    private javax.swing.JTextField valueNbSalleReserve;
    private javax.swing.JTextField valueNbSalleSE;
    private javax.swing.JTextField valueNbSalleTE;
    private javax.swing.JTextField valueTempsAnes;
    private javax.swing.JTextField valueTempsLiber;
    private javax.swing.JTextField valueTempsPrepa;
    // End of variables declaration//GEN-END:variables

// ============ POST EXECUTION ========================================================================

	private List<List<Tuple<LocalTime, LocalTime>>> attPatientRDV;
	private List<List<Tuple<LocalTime, LocalTime>>> attPatientUrgent;

	private List<List<Tuple<LocalTime, LocalTime>>> tmpLibreInfirmier;
	private List<List<Tuple<LocalTime, LocalTime>>> tmpLibreChirurgien;

	private List<List<Tuple<LocalTime, LocalTime>>> tmpOccSallesPE;
	private List<List<Tuple<LocalTime, LocalTime>>> tmpOccSallesSE;
	private List<List<Tuple<LocalTime, LocalTime>>> tmpOccSallesTE;

	private ChartPanel chartPanel;
	private JFreeChart chart;
	private DefaultCategoryDataset dataset;

	public void initialiserGraph() {
		labelImage.setVisible(false);
		labelLancez.setVisible(false);

		dataset = new DefaultCategoryDataset();
		chart = null;

		lireDonnees();
		panelChartTools.setVisible(true);
	}

	private void btnChartInfirmierActionPerformed(java.awt.event.ActionEvent evt) {
		dataset.clear();

		ajouterAuDatasetRessource(tmpLibreInfirmier, "infirmiers");

		if (chart == null)
			initChart();

		chart = ChartFactory.createLineChart("Temps libre des infirmieres", "Instant", "Nb d'infirmiers libres",
				dataset);
		chart.fireChartChanged();

		txtTitreGraph.setForeground(new Color(0, 0, 0));
		txtTitreGraph.setText("Nb d'infirmiers libres à chaque instant");
	}

	private void btnChartChirugienActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChartChirugienActionPerformed
		dataset.clear();

		ajouterAuDatasetRessource(tmpLibreChirurgien, "chirurgiens");

		if (chart == null)
			initChart();

		chart = ChartFactory.createLineChart("Temps libre des chirurgiens", "Instant", "Nb de chirurgiens libres",
				dataset);
		chart.fireChartChanged();

		txtTitreGraph.setForeground(new Color(0, 0, 0));
		txtTitreGraph.setText("Nb de chirurgiens libres à chaque instant");
	}// GEN-LAST:event_btnChartChirugienActionPerformed

	private void btnChartSalleActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChartSalleActionPerformed
		dataset.clear();

		boolean bool = !chkBoxPE.isSelected() && !chkBoxSE.isSelected() && !chkBoxTE.isSelected();

		if (bool || chkBoxPE.isSelected())
			ajouterAuDatasetRessource(tmpOccSallesPE, "salle PE");

		if (bool || chkBoxSE.isSelected())
			ajouterAuDatasetRessource(tmpOccSallesSE, "salle SE");

		if (bool || chkBoxTE.isSelected())
			ajouterAuDatasetRessource(tmpOccSallesTE, "salle TE");

		if (chart == null)
			initChart();

		chart = ChartFactory.createLineChart("Temps libre des chirurgiens", "Instant", "Nb de chirurgiens libres",
				dataset);

		chart.fireChartChanged();

		txtTitreGraph.setForeground(new Color(0, 0, 0));
		txtTitreGraph.setText("Nb de salles libres à chaque instant");
	}// GEN-LAST:event_btnChartSalleActionPerformed

	private void btnChartPatientActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChartPatientActionPerformed
		dataset.clear();
		dataset.setValue(5000, heureFinJournee, heureDebutJournee);

		boolean bool = !chkBoxRDV.isSelected() && !chkBoxUrgent.isSelected();

		if (bool || chkBoxRDV.isSelected()) {
			ajouterAuDatasetPatient(attPatientRDV, "RDV en attente de salle", 0);
			ajouterAuDatasetPatient(attPatientRDV, "RDV en attente de preparation de la salle", 1);
			ajouterAuDatasetPatient(attPatientRDV, "RDV en attente d'un chirugien", 2);
			ajouterAuDatasetPatient(attPatientRDV, "RDV en attente de liberation de la salle", 3);
		}

		if (bool || chkBoxUrgent.isSelected()) {
			ajouterAuDatasetPatient(attPatientUrgent, "Urgent en attente de salle", 0);
			ajouterAuDatasetPatient(attPatientUrgent, "Urgent en attente de preparation de la salle", 1);
			ajouterAuDatasetPatient(attPatientUrgent, "Urgent en attente d'un chirugien", 2);
			ajouterAuDatasetPatient(attPatientUrgent, "Urgent en attente de liberation de la salle", 3);
		}

		dataset.removeRow(LocalTime.of(18, 0));

		if (chart == null)
			initChart();

		chart = ChartFactory.createLineChart("Nb de patients en attente à chaque instant", "Instant",
				"Nb de patients en attente", dataset);
		chart.fireChartChanged();

		txtTitreGraph.setForeground(new Color(0, 0, 0));
		txtTitreGraph.setText("Nb de patients en attente à chaque instant");
	}// GEN-LAST:event_btnChartPatientActionPerformed

	private void ajouterAuDatasetRessource(List<List<Tuple<LocalTime, LocalTime>>> map, String intitule) {
		int groupage = 15;
		Map<LocalTime, Integer> chartMap = new HashMap<>();

		for (List<Tuple<LocalTime, LocalTime>> ressource : map) {
			for (Tuple<LocalTime, LocalTime> tuple : ressource) {
				LocalTime debut = tuple.getPremierElement();
				LocalTime fin = tuple.getSecondElement();

				while (!debut.isAfter(fin)) {
					int i = 0;

					if (chartMap.containsKey(debut)) {
						i = chartMap.get(debut);
					}

					chartMap.put(debut, i + 1);
					debut = debut.plusMinutes(1);
				}
			}
		}

		Set<LocalTime> absices = chartMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey).sorted(Comparator.comparing(LocalTime::getHour)
						.thenComparing(LocalTime::getMinute).thenComparing(LocalTime::getSecond))
				.collect(Collectors.toCollection(LinkedHashSet::new));

		Map<LocalTime, Integer> chartMapQte = new HashMap<>();
		for (LocalTime temps : absices) {
			LocalTime heureRef = temps.minusMinutes((temps.getHour() * 60 + temps.getMinute()) % groupage);

			if (heureRef != temps) {
				int i = 0;
				if (chartMap.containsKey(heureRef)) {
					i = chartMap.get(heureRef);
				}
				chartMap.put(heureRef, i + chartMap.get(temps));
			}

			int i = 1;
			if (chartMapQte.containsKey(heureRef)) {
				i = chartMapQte.get(heureRef);
			}

			chartMapQte.put(heureRef, i + 1);
		}

		absices = chartMapQte.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey)
				.sorted(Comparator.comparing(LocalTime::getHour).thenComparing(LocalTime::getMinute)
						.thenComparing(LocalTime::getSecond))
				.collect(Collectors.toCollection(LinkedHashSet::new));
		for (LocalTime temps : absices) {
			dataset.addValue(chartMap.get(temps) * 1.0 / chartMapQte.get(temps), intitule, temps);
		}
	}

	private void ajouterAuDatasetPatient(List<List<Tuple<LocalTime, LocalTime>>> map, String intitule, int indice) {
		int groupage = 15;
		Map<LocalTime, Integer> chartMap = new HashMap<>();

		for (List<Tuple<LocalTime, LocalTime>> ressource : map) {
			if (ressource.size() > indice) {
				Tuple<LocalTime, LocalTime> tuple = ressource.get(indice);
				LocalTime debut = tuple.getPremierElement();
				LocalTime fin = tuple.getSecondElement();

				while (!debut.isAfter(fin)) {
					int i = 0;

					if (chartMap.containsKey(debut)) {
						i = chartMap.get(debut);
					}

					chartMap.put(debut, i + 1);
					debut = debut.plusMinutes(1);
				}
			}
		}

		Set<LocalTime> absices = chartMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey).sorted(Comparator.comparing(LocalTime::getHour)
						.thenComparing(LocalTime::getMinute).thenComparing(LocalTime::getSecond))
				.collect(Collectors.toCollection(LinkedHashSet::new));

		Map<LocalTime, Integer> chartMapQte = new HashMap<>();
		for (LocalTime temps : absices) {
			LocalTime heureRef = temps.minusMinutes((temps.getHour() * 60 + temps.getMinute()) % groupage);

			if (heureRef != temps) {
				int i = 0;
				if (chartMap.containsKey(heureRef)) {
					i = chartMap.get(heureRef);
				}
				chartMap.put(heureRef, i + chartMap.get(temps));
			}

			int i = 1;
			if (chartMapQte.containsKey(heureRef)) {
				i = chartMapQte.get(heureRef);
			}

			chartMapQte.put(heureRef, i + 1);
		}

		absices = chartMapQte.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey)
				.sorted(Comparator.comparing(LocalTime::getHour).thenComparing(LocalTime::getMinute)
						.thenComparing(LocalTime::getSecond))
				.collect(Collectors.toCollection(LinkedHashSet::new));

		for (LocalTime temps : absices) {
			dataset.addValue(chartMap.get(temps) * 1.0 / chartMapQte.get(temps), intitule, temps);
		}
	}

	private void lireDonnees() {
		JSONParser parser = new JSONParser();
		try {
			FileReader file = new FileReader("../extraction.json");
			JSONObject objet = (JSONObject) parser.parse(file);

			/* Récupérations des temps d'attente des patients rendez-vous */
			JSONObject patientRDV = (JSONObject) objet.get("patientRDV");
			attPatientRDV = new ArrayList<>();

			JSONObject patient;

			int indice;
			String str = "0";
			for (indice = 1; patientRDV.containsKey(str); indice++) {
				List<Tuple<LocalTime, LocalTime>> nvListe = new ArrayList<Tuple<LocalTime, LocalTime>>();

				patient = (JSONObject) patientRDV.get(str);
				JSONArray tableau;

				tableau = (JSONArray) patient.get("ATTENTESALLE");

				if (tableau.size() > 0) {
					nvListe.add(new Tuple<LocalTime, LocalTime>(LocalTime.parse((String) tableau.get(0)),
							LocalTime.parse((String) tableau.get(1))));

					tableau = (JSONArray) patient.get("ATTENTEPREPARATION");

					if (tableau.size() > 0) {
						nvListe.add(new Tuple<LocalTime, LocalTime>(LocalTime.parse((String) tableau.get(0)),
								LocalTime.parse((String) tableau.get(1))));

						tableau = (JSONArray) patient.get("ATTENTECHIRURGIEN");

						if (tableau.size() > 0) {
							nvListe.add(new Tuple<LocalTime, LocalTime>(LocalTime.parse((String) tableau.get(0)),
									LocalTime.parse((String) tableau.get(1))));

							tableau = (JSONArray) patient.get("ATTENTELIBERATION");

							if (tableau.size() > 0) {
								nvListe.add(new Tuple<LocalTime, LocalTime>(LocalTime.parse((String) tableau.get(0)),
										LocalTime.parse((String) tableau.get(1))));
							}
						}
					}
				}
				attPatientRDV.add(nvListe);
				str = Integer.toString(indice);
			}

			/* Récupérations des temps d'attente des patients urgents */
			JSONObject patientUrgent = (JSONObject) objet.get("patientUrgent");
			attPatientUrgent = new ArrayList<>();

			for (indice = indice; patientUrgent.containsKey(str); indice++) {
				List<Tuple<LocalTime, LocalTime>> nvListe = new ArrayList<Tuple<LocalTime, LocalTime>>();

				patient = (JSONObject) patientUrgent.get(str);
				JSONArray tableau;

				tableau = (JSONArray) patient.get("ATTENTESALLE");

				if (tableau.size() > 0) {
					nvListe.add(new Tuple<LocalTime, LocalTime>(LocalTime.parse((String) tableau.get(0)),
							LocalTime.parse((String) tableau.get(1))));

					tableau = (JSONArray) patient.get("ATTENTEPREPARATION");

					if (tableau.size() > 0) {
						nvListe.add(new Tuple<LocalTime, LocalTime>(LocalTime.parse((String) tableau.get(0)),
								LocalTime.parse((String) tableau.get(1))));

						tableau = (JSONArray) patient.get("ATTENTECHIRURGIEN");

						if (tableau.size() > 0) {
							nvListe.add(new Tuple<LocalTime, LocalTime>(LocalTime.parse((String) tableau.get(0)),
									LocalTime.parse((String) tableau.get(1))));

							tableau = (JSONArray) patient.get("ATTENTELIBERATION");

							if (tableau.size() > 0) {
								nvListe.add(new Tuple<LocalTime, LocalTime>(LocalTime.parse((String) tableau.get(0)),
										LocalTime.parse((String) tableau.get(1))));
							}
						}
					}
				}
				attPatientUrgent.add(nvListe);
				str = Integer.toString(indice);
			}

			/* Récupérations des temps libres des infirmiers */
			JSONObject infirmiers = (JSONObject) objet.get("infirmier");
			tmpLibreInfirmier = new ArrayList<>();

			JSONArray infirmier;

			str = "0";
			for (indice = 1; infirmiers.containsKey(str); indice++) {
				List<Tuple<LocalTime, LocalTime>> nvListe = new ArrayList<Tuple<LocalTime, LocalTime>>();

				infirmier = (JSONArray) infirmiers.get(str);

				for (int indice2 = 0; infirmier.size() > indice2; indice2++) {
					JSONArray tableau = (JSONArray) infirmier.get(indice2);

					nvListe.add(new Tuple<LocalTime, LocalTime>(LocalTime.parse((String) tableau.get(0)),
							LocalTime.parse((String) tableau.get(1))));
				}

				tmpLibreInfirmier.add(nvListe);
				str = Integer.toString(indice);
			}

			/* Récupérations des temps libres des chirurgiens */
			JSONObject chirurgiens = (JSONObject) objet.get("chirugien");
			tmpLibreChirurgien = new ArrayList<>();

			JSONArray chirurgien;

			str = "0";
			for (indice = 1; chirurgiens.containsKey(str); indice++) {
				List<Tuple<LocalTime, LocalTime>> nvListe = new ArrayList<Tuple<LocalTime, LocalTime>>();

				chirurgien = (JSONArray) chirurgiens.get(str);

				for (int indice2 = 0; chirurgien.size() > indice2; indice2++) {
					JSONArray tableau = (JSONArray) chirurgien.get(indice2);

					nvListe.add(new Tuple<LocalTime, LocalTime>(LocalTime.parse((String) tableau.get(0)),
							LocalTime.parse((String) tableau.get(1))));
				}

				tmpLibreChirurgien.add(nvListe);
				str = Integer.toString(indice);
			}

			JSONObject salles = (JSONObject) objet.get("salle");

			/* Récupérations des temps libres des chirurgiens */
			JSONObject sallesPE = (JSONObject) salles.get("peuEquipe");
			tmpOccSallesPE = new ArrayList<>();

			JSONArray sallePE;

			str = "0";
			for (indice = 1; sallesPE.containsKey(str); indice++) {
				List<Tuple<LocalTime, LocalTime>> nvListe = new ArrayList<Tuple<LocalTime, LocalTime>>();

				sallePE = (JSONArray) sallesPE.get(str);

				for (int indice2 = 0; sallePE.size() > indice2; indice2++) {
					JSONArray tableau = (JSONArray) sallePE.get(indice2);

					nvListe.add(new Tuple<LocalTime, LocalTime>(LocalTime.parse((String) tableau.get(0)),
							LocalTime.parse((String) tableau.get(1))));
				}

				tmpOccSallesPE.add(nvListe);
				str = Integer.toString(indice);
			}

			/* Récupérations des temps libres des chirurgiens */
			JSONObject sallesSE = (JSONObject) salles.get("semiEquipe");
			tmpOccSallesSE = new ArrayList<>();

			JSONArray salleSE;

			str = "0";
			for (indice = 1; sallesSE.containsKey(str); indice++) {
				List<Tuple<LocalTime, LocalTime>> nvListe = new ArrayList<Tuple<LocalTime, LocalTime>>();

				salleSE = (JSONArray) sallesSE.get(str);

				for (int indice2 = 0; salleSE.size() > indice2; indice2++) {
					JSONArray tableau = (JSONArray) salleSE.get(indice2);

					nvListe.add(new Tuple<LocalTime, LocalTime>(LocalTime.parse((String) tableau.get(0)),
							LocalTime.parse((String) tableau.get(1))));
				}

				tmpOccSallesSE.add(nvListe);
				str = Integer.toString(indice);
			}

			/* Récupérations des temps libres des chirurgiens */
			JSONObject sallesTE = (JSONObject) salles.get("tresEquipe");
			tmpOccSallesTE = new ArrayList<>();

			JSONArray salleTE;

			str = "0";
			for (indice = 1; sallesTE.containsKey(str); indice++) {
				List<Tuple<LocalTime, LocalTime>> nvListe = new ArrayList<Tuple<LocalTime, LocalTime>>();

				salleTE = (JSONArray) sallesTE.get(str);

				for (int indice2 = 0; salleTE.size() > indice2; indice2++) {
					JSONArray tableau = (JSONArray) salleTE.get(indice2);

					nvListe.add(new Tuple<LocalTime, LocalTime>(LocalTime.parse((String) tableau.get(0)),
							LocalTime.parse((String) tableau.get(1))));
				}

				tmpOccSallesTE.add(nvListe);
				str = Integer.toString(indice);
			}
		} catch (Exception e) {
			System.err.println("Initialisation des graphes échouee :");
			e.printStackTrace();
		}
	}

	private void initChart() {
		chart = ChartFactory.createLineChart("", "Heures de la journée", "Temps attentes / libre", dataset);

		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		CategoryAxis xAxis = plot.getDomainAxis();

		xAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));

		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1100, 900));

		javax.swing.GroupLayout panelChartLayout = new javax.swing.GroupLayout(panelChart);
		panelChart.setLayout(panelChartLayout);
		panelChartLayout.setHorizontalGroup(panelChartLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(panelChartLayout.createSequentialGroup().addContainerGap(212, Short.MAX_VALUE)
						.addGroup(panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(labelLancez, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.PREFERRED_SIZE, 900,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(labelImage, javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(chartPanel, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(250)));
		panelChartLayout
				.setVerticalGroup(panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChartLayout.createSequentialGroup()
								.addGap(0, 27, Short.MAX_VALUE).addComponent(labelImage).addGap(18, 18, 18)
								.addComponent(labelLancez, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(30, 30, 30)
								.addComponent(chartPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));

		panelChart.revalidate();
		panelChart.repaint();
	}
}
