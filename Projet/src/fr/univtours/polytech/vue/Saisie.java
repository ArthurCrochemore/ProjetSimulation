/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fr.univtours.polytech.vue;

import com.orsoncharts.util.json.JSONObject;
import com.orsoncharts.util.json.parser.JSONParser;
import com.raven.event.EventTimePicker;

import fr.univtours.polytech.MainSimulation;
import sun.security.tools.keytool.Main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import static java.time.temporal.TemporalQueries.zone;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import org.jfree.data.category.DefaultCategoryDataset;

// ============ INITIALISATION ========================================================================

/**
 *
 * @author Utilisateur
 */
public class Saisie extends javax.swing.JFrame {
    
    private Integer regle1;
    private Integer regle2;
    private Integer regle3;

    //heures de la journee
    private LocalTime heureDebutJournee;
    private LocalTime heureFinJournee;

    //Nb de Ressource
    private Integer nbInfirmiere;
    private Integer nbChirurgien;
    private Integer nbSallesPeuEquipee;
    private Integer nbSallesSemiEquipee;
    private Integer nbSallesTresEquipee;
    private Integer nbSallesReserveesUrgence;

    //Constantes
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

        heureDebutJournee = LocalTime.of(8,0,0,0);
        heureFinJournee = LocalTime.of(18,0,0, 0);

        nbInfirmiere = 0;
        nbChirurgien = 0;
        nbSallesPeuEquipee = 0;
        nbSallesSemiEquipee = 0;
        nbSallesTresEquipee = 0;
        nbSallesReserveesUrgence = 0;

        tempsPreparation = 0;
        tempsAnesthesie = 0;
        tempsLiberation = 0;
	moyTempsOperation = 0;
        marge = 0;

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
            }
        });
        heureDebutJournee = LocalTime.of(8, 0);
        btnHeureDebut.setText(heureDebutJournee.toString());
        pickerHeureDebut.setSelectedTime(Date.from((Instant) heureDebutJournee.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant()));
        pickerHeureFin.addEventTimePicker(new EventTimePicker() {
            @Override
            public void timeSelected(String string) {
                heureFinJournee = getHeure(pickerHeureFin);
                btnHeureFin.setText(heureFinJournee.toString());                
            }
        });
        heureFinJournee = LocalTime.of(18, 0);
        btnHeureFin.setText(heureFinJournee.toString());
        pickerHeureFin.setSelectedTime(Date.from((Instant) heureFinJournee.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant()));
    }
    
    private LocalTime getHeure(com.raven.swing.TimePicker picker) {
        String str = picker.getSelectedTime();
        char[] dest = new char[4];
        str.getChars(0, 2, dest, 0);
        str.getChars(3, 5, dest, 2);

        int i1  = (((int) dest[0]) - 48)* 10 + (((int) dest[1]) - 48);
        int i2  = (((int) dest[2]) - 48)* 10 + (((int) dest[3]) - 48);
        return  LocalTime.of(i1, i2);
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pickerHeureDebut = new com.raven.swing.TimePicker();
        pickerHeureFin = new com.raven.swing.TimePicker();
        panelSaisie = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnHeureDebut = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btnHeureFin = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        stringRegleGestion1 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        stringRegleGestion2 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        stringRegleGestion3 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        valueNbSalleReserve = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        valueNbInfirmier1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        valueNbChirurgien1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        valueNbSallePE1 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        btnOpenSaisiePatient = new javax.swing.JButton();
        btnSimulation = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        valueNbInfirmier = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        valueNbChirurgien = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        valueNbSallePE = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        valueNbSalleSE = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        valueNbSalleTE = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        valueMoyTempsOpe = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        valueMarge = new javax.swing.JTextField();
        panelChart = new javax.swing.JPanel();
        labelLancez = new javax.swing.JLabel();
        labelImage = new javax.swing.JLabel();
        panelChartTools = new javax.swing.JPanel();
        btnChartInfirmier = new javax.swing.JButton();
        btnChartChirugien = new javax.swing.JButton();
        btnChartSalle = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();

        pickerHeureDebut.setForeground(new java.awt.Color(236, 213, 129));

        pickerHeureFin.setForeground(new java.awt.Color(236, 213, 129));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFocusCycleRoot(false);
        setMinimumSize(new java.awt.Dimension(1280, 720));

        panelSaisie.setBackground(new java.awt.Color(236, 213, 129));
        panelSaisie.setMinimumSize(new java.awt.Dimension(300, 720));
        panelSaisie.setPreferredSize(new java.awt.Dimension(470, 1000));

        jLabel9.setText("Heure début simulation :");

        btnHeureDebut.setBackground(new java.awt.Color(236, 213, 129));
        btnHeureDebut.setText("8:00");
        btnHeureDebut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHeureDebutActionPerformed(evt);
            }
        });

        jLabel10.setText("Heure fin simulation :");

        btnHeureFin.setBackground(new java.awt.Color(236, 213, 129));
        btnHeureFin.setText("18:00");
        btnHeureFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHeureFinActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Horraires");
        jLabel19.setPreferredSize(new java.awt.Dimension(400, 20));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Règles de Gestion");
        jLabel17.setPreferredSize(new java.awt.Dimension(400, 20));

        jLabel12.setText("Gestion Priorité Infirmiers :");

        stringRegleGestion1.setBackground(new java.awt.Color(236, 213, 129));
        stringRegleGestion1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Priorite aux salles en attente de preparation", "Priorite aux salles en attente de liberation", "Dans l'ordre des attentes", "Priorite aux salles d'urgence" }));
        stringRegleGestion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stringRegleGestion1ActionPerformed(evt);
            }
        });

        jLabel13.setText("Gestion Attribution des salles :");

        stringRegleGestion2.setBackground(new java.awt.Color(236, 213, 129));
        stringRegleGestion2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Priorité premier en attente, salle Réservée statique", "Priorité rendez-vous", "Priorité urgence", "Priorité premier en attente, salle Réservée dynamique" }));
        stringRegleGestion2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stringRegleGestion2ActionPerformed(evt);
            }
        });

        jLabel14.setText("Gestion Priorité Chirurgiens :");

        stringRegleGestion3.setBackground(new java.awt.Color(236, 213, 129));
        stringRegleGestion3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dans l'ordre des attentes", "Priorite aux salles d'urgence" }));
        stringRegleGestion3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stringRegleGestion3ActionPerformed(evt);
            }
        });

        jLabel15.setText("Nombre Salle Réservées  :");

        valueNbSalleReserve.setBackground(new java.awt.Color(236, 213, 129));
        valueNbSalleReserve.setText("0");
        valueNbSalleReserve.setPreferredSize(new java.awt.Dimension(70, 22));
        valueNbSalleReserve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueNbSalleReserveActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Constantes");
        jLabel16.setPreferredSize(new java.awt.Dimension(400, 20));

        jLabel6.setText("Temps Prépartion des Salles (en min) :");

        valueNbInfirmier1.setBackground(new java.awt.Color(236, 213, 129));
        valueNbInfirmier1.setText("0");
        valueNbInfirmier1.setPreferredSize(new java.awt.Dimension(70, 22));
        valueNbInfirmier1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueNbInfirmier1ActionPerformed(evt);
            }
        });

        jLabel7.setText("Temps Anesthésie des Patients (en min) :");

        valueNbChirurgien1.setBackground(new java.awt.Color(236, 213, 129));
        valueNbChirurgien1.setText("0");
        valueNbChirurgien1.setPreferredSize(new java.awt.Dimension(70, 22));
        valueNbChirurgien1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueNbChirurgien1ActionPerformed(evt);
            }
        });

        jLabel8.setText("Temps Libération des Salles (en min) :");

        valueNbSallePE1.setBackground(new java.awt.Color(236, 213, 129));
        valueNbSallePE1.setText("0");
        valueNbSallePE1.setPreferredSize(new java.awt.Dimension(70, 22));
        valueNbSallePE1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueNbSallePE1ActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Saisie Patients");
        jLabel21.setPreferredSize(new java.awt.Dimension(400, 20));

        btnOpenSaisiePatient.setBackground(new java.awt.Color(236, 213, 129));
        btnOpenSaisiePatient.setText("Ouvrir Fenetre de Saisie");
        btnOpenSaisiePatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenSaisiePatientActionPerformed(evt);
            }
        });

        btnSimulation.setBackground(new java.awt.Color(236, 213, 129));
        btnSimulation.setText("Lancer Simulation");
        btnSimulation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimulationActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Quantité de Ressources");
        jLabel11.setPreferredSize(new java.awt.Dimension(400, 20));

        jLabel1.setText("Nombre Infirmier :");

        valueNbInfirmier.setBackground(new java.awt.Color(236, 213, 129));
        valueNbInfirmier.setText("0");
        valueNbInfirmier.setPreferredSize(new java.awt.Dimension(70, 22));
        valueNbInfirmier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueNbInfirmierActionPerformed(evt);
            }
        });

        jLabel2.setText("Nombre Chirurgien :");

        valueNbChirurgien.setBackground(new java.awt.Color(236, 213, 129));
        valueNbChirurgien.setText("0");
        valueNbChirurgien.setPreferredSize(new java.awt.Dimension(70, 22));
        valueNbChirurgien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueNbChirurgienActionPerformed(evt);
            }
        });

        jLabel3.setText("Nombre Salle Peu équipée :");

        valueNbSallePE.setBackground(new java.awt.Color(236, 213, 129));
        valueNbSallePE.setText("0");
        valueNbSallePE.setPreferredSize(new java.awt.Dimension(70, 22));
        valueNbSallePE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueNbSallePEActionPerformed(evt);
            }
        });

        jLabel4.setText("Nombre Salle Semi équipée :");

        valueNbSalleSE.setBackground(new java.awt.Color(236, 213, 129));
        valueNbSalleSE.setText("0");
        valueNbSalleSE.setPreferredSize(new java.awt.Dimension(70, 22));
        valueNbSalleSE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueNbSalleSEActionPerformed(evt);
            }
        });

        jLabel5.setText("Nombre Salle Très équipée :");

        valueNbSalleTE.setBackground(new java.awt.Color(236, 213, 129));
        valueNbSalleTE.setText("0");
        valueNbSalleTE.setPreferredSize(new java.awt.Dimension(70, 22));
        valueNbSalleTE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueNbSalleTEActionPerformed(evt);
            }
        });

        jLabel18.setText("Moyenne des temps d'Operation (en min) :");

        valueMoyTempsOpe.setBackground(new java.awt.Color(236, 213, 129));
        valueMoyTempsOpe.setText("0");
        valueMoyTempsOpe.setPreferredSize(new java.awt.Dimension(70, 22));
        valueMoyTempsOpe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueMoyTempsOpeActionPerformed(evt);
            }
        });

        jLabel20.setText("Marge pour le temps Moyen (en %) :");

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
                .addGap(66, 66, 66)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addGap(30, 30, 30)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHeureDebut)
                    .addComponent(btnHeureFin))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelSaisieLayout.createSequentialGroup()
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSaisieLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSaisieLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelSaisieLayout.createSequentialGroup()
                                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel6))
                                        .addGap(18, 18, 18)
                                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(valueNbChirurgien1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(valueNbInfirmier1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(valueNbSallePE1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(panelSaisieLayout.createSequentialGroup()
                                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel18)
                                            .addComponent(jLabel20))
                                        .addGap(18, 18, 18)
                                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(valueMoyTempsOpe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(valueMarge, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 75, Short.MAX_VALUE))
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelSaisieLayout.createSequentialGroup()
                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSaisieLayout.createSequentialGroup()
                                .addGap(124, 124, 124)
                                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnOpenSaisiePatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSimulation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(panelSaisieLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13)
                                    .addGroup(panelSaisieLayout.createSequentialGroup()
                                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel15))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(valueNbSalleReserve, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(stringRegleGestion2, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(stringRegleGestion1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(stringRegleGestion3, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelSaisieLayout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelSaisieLayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(67, 67, 67)
                                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(valueNbSalleTE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(valueNbSalleSE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(panelSaisieLayout.createSequentialGroup()
                                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(valueNbSallePE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(valueNbChirurgien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(valueNbInfirmier, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 61, Short.MAX_VALUE))
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelSaisieLayout.setVerticalGroup(
            panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSaisieLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHeureDebut)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(btnHeureFin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stringRegleGestion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addGap(12, 12, 12)
                .addComponent(stringRegleGestion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jLabel14)
                .addGap(5, 5, 5)
                .addComponent(stringRegleGestion3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(valueNbSalleReserve, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(26, 26, 26)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(valueNbInfirmier1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(valueNbChirurgien1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(valueNbSallePE1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(valueMoyTempsOpe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(valueMarge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(panelSaisieLayout.createSequentialGroup()
                        .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(valueNbInfirmier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addComponent(valueNbChirurgien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(valueNbSallePE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(valueNbSalleSE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelSaisieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(valueNbSalleTE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnOpenSaisiePatient)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(btnSimulation)
                .addGap(33, 33, 33))
        );

        getContentPane().add(panelSaisie, java.awt.BorderLayout.CENTER);

        panelChart.setBackground(new java.awt.Color(255, 255, 255));
        panelChart.setForeground(new java.awt.Color(255, 255, 255));
        panelChart.setMinimumSize(new java.awt.Dimension(980, 720));
        panelChart.setPreferredSize(new java.awt.Dimension(1370, 1000));

        labelLancez.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelLancez.setForeground(new java.awt.Color(51, 51, 51));
        labelLancez.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelLancez.setText("Lancez une simulation pour voir vos resultats s'afficher ici");
        labelLancez.setPreferredSize(new java.awt.Dimension(1480, 25));

        labelImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fr/univtours/polytech/vue/image.jpg"))); // NOI18N

        panelChartTools.setBackground(new java.awt.Color(153, 153, 153));

        btnChartInfirmier.setText("Attente Infirmiers");
        btnChartInfirmier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChartInfirmierActionPerformed(evt);
            }
        });

        btnChartChirugien.setText("Attente Chirurgiens");

        btnChartSalle.setText("Attente Salles");

        jCheckBox1.setText("Peu équipées");

        jCheckBox2.setText("Semi équipées");

        jCheckBox3.setText("Très équipées");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        jCheckBox4.setText("Réservées");

        jButton1.setText("Attente Patients");

        jCheckBox5.setText("RDV");

        jCheckBox6.setText("Urgent");

        javax.swing.GroupLayout panelChartToolsLayout = new javax.swing.GroupLayout(panelChartTools);
        panelChartTools.setLayout(panelChartToolsLayout);
        panelChartToolsLayout.setHorizontalGroup(
            panelChartToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChartToolsLayout.createSequentialGroup()
                .addGroup(panelChartToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelChartToolsLayout.createSequentialGroup()
                        .addGroup(panelChartToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelChartToolsLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(panelChartToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox1)
                                    .addComponent(jCheckBox3)
                                    .addComponent(jCheckBox2)
                                    .addComponent(jCheckBox4)))
                            .addGroup(panelChartToolsLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(panelChartToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBox6)
                                    .addComponent(jCheckBox5))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelChartToolsLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(panelChartToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnChartInfirmier, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnChartChirugien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnChartSalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox4)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelChartLayout = new javax.swing.GroupLayout(panelChart);
        panelChart.setLayout(panelChartLayout);
        panelChartLayout.setHorizontalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChartLayout.createSequentialGroup()
                .addContainerGap(212, Short.MAX_VALUE)
                .addGroup(panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelLancez, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelImage, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(81, 81, 81)
                .addComponent(panelChartTools, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelChartLayout.setVerticalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChartLayout.createSequentialGroup()
                .addComponent(panelChartTools, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChartLayout.createSequentialGroup()
                .addGap(0, 27, Short.MAX_VALUE)
                .addComponent(labelImage)
                .addGap(18, 18, 18)
                .addComponent(labelLancez, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
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
        } catch(NumberFormatException e) {
            textField.setText("0");
            return 0;
        }
        return valeur;
    }
    private void valueNbInfirmierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueNbInfirmierActionPerformed
        // TODO add your handling code here:
        nbInfirmiere = recupererValeurLue(valueNbInfirmier);
    }//GEN-LAST:event_valueNbInfirmierActionPerformed

    private void valueNbChirurgienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueNbChirurgienActionPerformed
        // TODO add your handling code here:
        nbChirurgien = recupererValeurLue(valueNbChirurgien);
    }//GEN-LAST:event_valueNbChirurgienActionPerformed

    private void valueNbSallePEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueNbSallePEActionPerformed
        // TODO add your handling code here:
        nbSallesPeuEquipee = recupererValeurLue(valueNbSallePE);
    }//GEN-LAST:event_valueNbSallePEActionPerformed

    private void valueNbSalleSEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueNbSalleSEActionPerformed
        // TODO add your handling code here:
        nbSallesSemiEquipee = recupererValeurLue(valueNbSalleSE);
    }//GEN-LAST:event_valueNbSalleSEActionPerformed

    private void valueNbSalleTEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueNbSalleTEActionPerformed
        // TODO add your handling code here:
        nbSallesTresEquipee = recupererValeurLue(valueNbSalleTE);
    }//GEN-LAST:event_valueNbSalleTEActionPerformed

    private void btnOpenSaisiePatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenSaisiePatientActionPerformed
        // Ouvre la fentre SaisiePatient:
        
        SaisiePatient fenetreSaisiePatient = new SaisiePatient(this, nbPatientsRDVPE, nbPatientsRDVSE, nbPatientsRDVTE, nbPatientsUrgent, map);
        fenetreSaisiePatient.setLocationRelativeTo(null);
        fenetreSaisiePatient.setExtendedState( JFrame.MAXIMIZED_BOTH);
        fenetreSaisiePatient.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        fenetreSaisiePatient.setVisible(true);
    }//GEN-LAST:event_btnOpenSaisiePatientActionPerformed

    private void valueNbInfirmier1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueNbInfirmier1ActionPerformed
        // TODO add your handling code here:
        tempsPreparation = recupererValeurLue(valueNbInfirmier1);
    }//GEN-LAST:event_valueNbInfirmier1ActionPerformed

    private void valueNbChirurgien1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueNbChirurgien1ActionPerformed
        // TODO add your handling code here:
        tempsAnesthesie = recupererValeurLue(valueNbChirurgien1);
    }//GEN-LAST:event_valueNbChirurgien1ActionPerformed

    private void valueNbSallePE1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueNbSallePE1ActionPerformed
        // TODO add your handling code here:
        tempsLiberation = recupererValeurLue(valueNbSallePE1);
    }//GEN-LAST:event_valueNbSallePE1ActionPerformed

    private void btnHeureDebutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHeureDebutActionPerformed
        // TODO add your handling code here:
        pickerHeureDebut.showPopup(this, 350, 0);
    }//GEN-LAST:event_btnHeureDebutActionPerformed

    private void btnHeureFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHeureFinActionPerformed
        // TODO add your handling code here:
        pickerHeureFin.showPopup(this, 350, 30);
    }//GEN-LAST:event_btnHeureFinActionPerformed

    private void btnSimulationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimulationActionPerformed
        // TODO add your handling code here:
        try {
            PrintWriter writer = new PrintWriter("../fichier.txt", "UTF-8");
            
            writer.println(regle1);
            writer.println(regle2);
            writer.println(regle3);
            writer.println(heureDebutJournee.toString());
            writer.println(heureFinJournee.toString());
            writer.println(nbInfirmiere);
            writer.println(nbChirurgien);
            
            LocalTime heurePreparation = LocalTime.of((tempsPreparation/60)%24, tempsPreparation%60);
            writer.println(heurePreparation.toString());
            LocalTime heureAnesthesie = LocalTime.of((tempsAnesthesie/60)%24, tempsAnesthesie%60);
            writer.println(heureAnesthesie.toString());
            LocalTime heureLiberation = LocalTime.of((tempsLiberation/60)%24, tempsLiberation%60);
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
            
            for (Integer indice = 0; indice < nbPatientsRDVTE + nbPatientsRDVSE +nbPatientsRDVPE; indice++) {
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
        
        MainSimulation.main(null);
        
        //initialiserGraph();
    }//GEN-LAST:event_btnSimulationActionPerformed

    private void valueNbSalleReserveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueNbSalleReserveActionPerformed
        // TODO add your handling code here:
        nbSallesReserveesUrgence = recupererValeurLue(valueNbSalleReserve);
    }//GEN-LAST:event_valueNbSalleReserveActionPerformed

    private void stringRegleGestion3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stringRegleGestion3ActionPerformed
        // TODO add your handling code here:
        String str = stringRegleGestion3.getSelectedItem().toString();

        if (str.equals("Dans l'ordre des attentes")) {
            regle3 = 1;
        } else {
            regle3 = 2;
        }
    }//GEN-LAST:event_stringRegleGestion3ActionPerformed

    private void stringRegleGestion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stringRegleGestion1ActionPerformed
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
    }//GEN-LAST:event_stringRegleGestion1ActionPerformed

    private void stringRegleGestion2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stringRegleGestion2ActionPerformed
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
    }//GEN-LAST:event_stringRegleGestion2ActionPerformed

    private void valueMoyTempsOpeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueMoyTempsOpeActionPerformed
        // TODO add your handling code here:
        moyTempsOperation = recupererValeurLue(valueMoyTempsOpe);
    }//GEN-LAST:event_valueMoyTempsOpeActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void btnChartInfirmierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChartInfirmierActionPerformed
        String ouai = "";
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(marge, regle1, ouai);
    }//GEN-LAST:event_btnChartInfirmierActionPerformed

    private void valueMargeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueMargeActionPerformed
        // TODO add your handling code here:
        marge = recupererValeurLue(valueMarge);
    }//GEN-LAST:event_valueMargeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Saisie().setVisible(true);
            }
        });
        
    }
    
    private void extraireDonnee() {
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChartChirugien;
    private javax.swing.JButton btnChartInfirmier;
    private javax.swing.JButton btnChartSalle;
    private javax.swing.JButton btnHeureDebut;
    private javax.swing.JButton btnHeureFin;
    private javax.swing.JButton btnOpenSaisiePatient;
    private javax.swing.JButton btnSimulation;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JTextField valueMarge;
    private javax.swing.JTextField valueMoyTempsOpe;
    private javax.swing.JTextField valueNbChirurgien;
    private javax.swing.JTextField valueNbChirurgien1;
    private javax.swing.JTextField valueNbInfirmier;
    private javax.swing.JTextField valueNbInfirmier1;
    private javax.swing.JTextField valueNbSallePE;
    private javax.swing.JTextField valueNbSallePE1;
    private javax.swing.JTextField valueNbSalleReserve;
    private javax.swing.JTextField valueNbSalleSE;
    private javax.swing.JTextField valueNbSalleTE;
    // End of variables declaration//GEN-END:variables


// ============ POST EXECUTION ========================================================================
    
    JSONObject patientRDV;
            
    JSONObject patientUrgent;
    JSONObject infirmier;
    JSONObject chirurgien;

    JSONObject sallePE;
    JSONObject salleSE;
    JSONObject salleTE;
    
    public void initialiserGraph() {
        panelChartTools.setVisible(true);
        labelImage.setVisible(false);
        labelLancez.setVisible(false);
        
        JSONParser parser = new JSONParser();
        try {
            JSONObject objet = (JSONObject) parser.parse(new FileReader("../extraction.json"));
            
            patientRDV = (JSONObject) objet.get("patientRDV");
            
            patientUrgent = (JSONObject) objet.get("patientUrgent");
            infirmier = (JSONObject) objet.get("infirmier");
            chirurgien = (JSONObject) objet.get("chirugien");
            JSONObject salles = (JSONObject) objet.get("salle");
            
            sallePE = (JSONObject) salles.get("peuEquipe");
            salleSE = (JSONObject) salles.get("semiEquipe");
            salleTE = (JSONObject) salles.get("tresEquipe");
            
        }catch(Exception e) {
            System.err.println("Initialisation des graphes échouee");
        }
    }
}
