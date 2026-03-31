/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.com.ditramites.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import senadi.com.ditramites.model.BreederForms;
import senadi.com.ditramites.model.DenominationForms;
import senadi.com.ditramites.model.FileAnnexesApplication;
import senadi.com.ditramites.model.HallmarkForm;
import senadi.com.ditramites.model.OppositionForms;
import senadi.com.ditramites.model.PatentForms;
import senadi.com.ditramites.model.PlayForm;
import senadi.com.ditramites.model.RenewalForm;
import senadi.com.ditramites.model.ScopeForms;
import senadi.com.ditramites.model.TutelageForms;
import senadi.com.ditramites.model.VoucherForm;
import senadi.com.ditramites.model.mod.FileAnnex;
import senadi.com.ditramites.util.Operaciones;
import senadi.com.ditramites.util.ParametrosBD;

/**
 *
 * @author micharesp
 */
public class DAOConsultasForm {

    public DenominationForms getDenominationForm(String applicationNumber) {
        String query = "Select * "
                + "from denomination_forms as df "
                + "where df.application_number = '" + applicationNumber + "'";

        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            DenominationForms denomination = new DenominationForms();
            while (rs.next()) {
                denomination.setId(rs.getInt("id"));
                denomination.setPaymentReceiptId(rs.getInt("payment_receipt_id"));
                denomination.setOwnerId(rs.getInt("owner_id"));
                denomination.setApplicationNumber(rs.getString("application_number"));
                denomination.setApplicationDate(rs.getTimestamp("application_date"));
                denomination.setDenomination(rs.getString("denomination"));
                denomination.setPartOfProcess(rs.getString("part_of_process"));
                denomination.setInspectionPlace(rs.getString("inspection_place"));
                denomination.setStatus(rs.getString("status"));
                denomination.setDiscountFile(rs.getString("discount_file"));
            }

            con.close();
            return denomination;
        } catch (SQLException ex) {
            System.err.println("Error en obtener denomination_forms " + applicationNumber + ": " + ex);
            return new DenominationForms();
        }
    }

    public String getDenominationNombreArchivo(String archivo) {
        String query = "SELECT da.name FROM "
                + "denomination_annexes_data AS dad "
                + "INNER JOIN denomination_annexes AS da ON da.id = dad.denomination_annex_id "
                + "WHERE dad.file = '" + archivo + "'";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            String nombre = "";
            while (rs.next()) {
                nombre = rs.getString("name");
            }
            con.close();
            return nombre;
        } catch (SQLException ex) {
            System.out.println("error al obtener denomination nombre documento " + archivo + ": " + ex);
            return "";
        }
    }

    public BreederForms getBreederForm(String applicationNumber) {
        String query = "Select * "
                + "from breeder_forms as bf "
                + "where bf.application_number = '" + applicationNumber + "'";

        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            BreederForms breeder = new BreederForms();
            while (rs.next()) {
                breeder.setId(rs.getInt("id"));
                breeder.setApplicationDate(rs.getTimestamp("application_date"));
                breeder.setApplicationNumber(rs.getString("application_number"));
                breeder.setCommercialName(rs.getString("commercial_name"));
                breeder.setCreateDate(rs.getTimestamp("create_date"));
                breeder.setDiscountFile(rs.getString("discount_file"));
                breeder.setGroup(rs.getString("group"));
                breeder.setOwnerId(rs.getInt("owner_id"));
                breeder.setPaymentReceiptId(rs.getInt("payment_receipt_id"));
                breeder.setProposedName(rs.getString("proposed_name"));
                breeder.setStatus(rs.getString("status"));
            }

            con.close();
            return breeder;
        } catch (SQLException ex) {
            System.err.println("Error en obtener breeder " + applicationNumber + ": " + ex);
            return new BreederForms();
        }
    }

    public HallmarkForm getHallmarkForm(String applicationNumber) {

        String query = "Select hf.id, hf.niza_class_id, hf.payment_receipt_id, hf.application_date, hf.application_number,"
                + "hf.create_date, hf.denomination, hf.description, hf.expedient, hf.owner_id, hf.priority_number, hf.status,"
                + "t1.name as naturalezasigno, t2.name as tiposigno, ow.firstname, ow.lastname, lo.id as casillero, hf.discount_file "
                + "from hallmark_forms as hf "
                + "INNER JOIN form_types AS ft1 ON ft1.id = hf.hallmark_nature_id "
                + "INNER JOIN forms AS f1 ON f1.id = ft1.form_id "
                + "INNER JOIN types AS t1 ON t1.id = ft1.type_id "
                + "INNER JOIN form_types AS ft2 ON ft2.id = hf.hallmark_type_id "
                + "INNER JOIN forms AS f2 ON f2.id = ft2.form_id "
                + "INNER JOIN types AS t2 ON t2.id = ft2.type_id "
                + "INNER JOIN iepi_casilleros.owners as ow ON ow.id = hf.owner_id "
                + "INNER JOIN iepi_casilleros.lockers as lo ON lo.owner_id = ow.id "
                + "where hf.application_number = '" + applicationNumber + "'";

        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            HallmarkForm marca = new HallmarkForm();
            while (rs.next()) {
                marca.setId(rs.getInt("id"));
                marca.setNizaClassId(rs.getInt("niza_class_id"));
                marca.setPaymentReceiptId(rs.getInt("payment_receipt_id"));
                marca.setApplicationDate(rs.getTimestamp("application_date"));
                marca.setApplicationNumber(rs.getString("application_number"));
                marca.setCreateDate(rs.getTimestamp("create_date"));
                marca.setDenomination(rs.getString("denomination"));
                marca.setDescription(rs.getString("description"));
                marca.setExpedient(rs.getString("expedient"));
                marca.setOwnerId(rs.getInt("owner_id"));
                marca.setPriorityNumber(rs.getString("priority_number"));
                marca.setStatus(rs.getString("status"));
                marca.setTipoSigno(rs.getString("tiposigno"));
                marca.setNaturalezaSigno(rs.getString("naturalezasigno"));
                marca.setCasillero(rs.getInt("casillero") + "");
                marca.setDiscountFile(rs.getString("discount_file"));

            }

            con.close();
            return marca;
        } catch (Exception ex) {
            System.out.println("error en obtener marca " + applicationNumber + ": " + ex);
            return new HallmarkForm();
        }
    }

    public List<ScopeForms> getScopeFormsByAfforApplicationNumber(String afforApplicationNumber, boolean aviso) {
        String query;
        String cas = "INNER JOIN iepi_casilleros.owners as ow ON ow.id = sf.owner_id "
                + "INNER JOIN iepi_casilleros.lockers as lo ON lo.owner_id = ow.id ";
        if (aviso) {
            query = "Select * from scope_forms as sf "
                    + cas
                    + "where sf.application_number = '" + afforApplicationNumber + "'";
        } else {
            query = "Select * from scope_forms as sf "
                    + cas
                    + "where sf.affected_application_number = '" + afforApplicationNumber + "'";
        }

        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            List<ScopeForms> scopesForms = new ArrayList<>();
            while (rs.next()) {
                ScopeForms scope = new ScopeForms();
                scope.setAffectedApplicationNumber(rs.getString("affected_application_number"));
                scope.setApplicationDate(rs.getTimestamp("application_date"));
                scope.setApplicationNumber(rs.getString("application_number"));
                scope.setDescription(rs.getString("description"));
                scope.setExpedient(rs.getString("expedient"));
                scope.setFormTypeId(rs.getInt("form_type_id"));
                scope.setId(rs.getInt("id"));
                scope.setOwnerId(rs.getInt("owner_id"));
                scope.setCasillero(rs.getInt("lo.id") + "");

                String status = rs.getString("status");
                if (status.equals("SAVED") || status.equals("PREVIEW")) {
                    scope.setStatus("ALCANCE NO ENVIADO");
                } else {
                    scope.setStatus("ALCANCE ENVIADO");
                }
                scopesForms.add(scope);
            }
            con.close();
            return scopesForms;
        } catch (Exception ex) {
            System.out.println("error al obtener alcances del trámite " + afforApplicationNumber + ": " + ex);
            return new ArrayList<>();
        }
    }

    public List<TutelageForms> getTutelageFormsByExpedient(String expedient) {
        String query = "Select * from tutelage_forms as tf "
                + "INNER JOIN iepi_casilleros.owners as ow ON ow.id = tf.owner_id "
                + "INNER JOIN iepi_casilleros.lockers as lo ON lo.owner_id = ow.id "
                + "where tf.expedient = '" + expedient + "'";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            List<TutelageForms> tutelagesForms = new ArrayList<>();
            while (rs.next()) {
                TutelageForms tutelage = new TutelageForms();
                tutelage.setApplicationDate(Operaciones.formatTimesTamp(rs.getTimestamp("application_date")));
                tutelage.setApplicationNumber(rs.getString("application_number"));
                tutelage.setExpedient(rs.getString("expedient"));
                tutelage.setFormTypeId(rs.getInt("form_type_id"));
                tutelage.setId(rs.getInt("id"));
                tutelage.setOwnerId(rs.getInt("owner_id"));
                tutelage.setPaymentReceiptId(rs.getInt("payment_receipt_id"));
                tutelage.setRespondentDescription(rs.getString("respondent_description"));
                tutelage.setStatus(rs.getString("status"));
                tutelage.setSubject(rs.getString("subject"));
                tutelage.setCasillero(rs.getInt("lo.id") + "");

                tutelagesForms.add(tutelage);
            }
            con.close();
            return tutelagesForms;
        } catch (Exception ex) {
            System.out.println("error al obtener tutelages del expediente " + expedient + ": " + ex);
            return new ArrayList<>();
        }
    }

    public TutelageForms getTutelageFormsByApplicationNumber(String applicationNumber) {
        String query = "Select * from tutelage_forms as tf "
                + "INNER JOIN iepi_casilleros.owners as ow ON ow.id = tf.owner_id "
                + "INNER JOIN iepi_casilleros.lockers as lo ON lo.owner_id = ow.id "
                + "where tf.application_number = '" + applicationNumber + "'";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            TutelageForms tutelage = new TutelageForms();
            while (rs.next()) {
                Timestamp taux = rs.getTimestamp("application_date");
                if (taux != null) {
                    tutelage.setApplicationDate(Operaciones.formatTimesTamp(taux));
                }
                tutelage.setApplicationNumber(rs.getString("application_number"));
                tutelage.setExpedient(rs.getString("expedient"));
                tutelage.setFormTypeId(rs.getInt("form_type_id"));
                tutelage.setId(rs.getInt("id"));
                tutelage.setOwnerId(rs.getInt("owner_id"));
                tutelage.setPaymentReceiptId(rs.getInt("payment_receipt_id"));
                tutelage.setRespondentDescription(rs.getString("respondent_description"));
                tutelage.setStatus(rs.getString("status"));
                tutelage.setSubject(rs.getString("subject"));
                tutelage.setCasillero(rs.getString("lo.id") + "");
            }
            con.close();
            return tutelage;
        } catch (Exception ex) {
            System.out.println("error al obtener tutelages del tramite " + applicationNumber + ": " + ex);
            return new TutelageForms();
        }
    }

    public List<OppositionForms> getOppositionFormsBySearchedApplicationNumber(String searchedApplicationNumber) {
        String query = "Select * from opposition_forms as of "
                + "INNER JOIN iepi_casilleros.owners as ow ON ow.id = of.owner_id "
                + "INNER JOIN iepi_casilleros.lockers as lo ON lo.owner_id = ow.id "
                + "where of.searched_application_number = '" + searchedApplicationNumber + "'";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            List<OppositionForms> oppositions = new ArrayList<>();
            while (rs.next()) {
                OppositionForms opposition = new OppositionForms();
                Timestamp taux = rs.getTimestamp("application_date");
                if (taux != null) {
                    opposition.setApplicationDate(Operaciones.formatTimesTamp(taux));
                }
                opposition.setApplicationNumber(rs.getString("application_number"));
                opposition.setFormId(rs.getInt("form_id"));
                opposition.setExpedient(rs.getString("opposition_application_number"));
                opposition.setId(rs.getInt("id"));
                opposition.setOwnerId(rs.getInt("owner_id"));
                opposition.setPaymentReceiptId(rs.getInt("payment_receipt_id"));

                opposition.setPowerAttorney(rs.getString("power_attorney"));
                opposition.setSearchClue(rs.getString("search_clue"));
                opposition.setSearchedApplicationNumber(rs.getString("searched_application_number"));
                opposition.setStatus(rs.getString("status"));

                opposition.setCasillero(rs.getInt("lo.id") + "");

                oppositions.add(opposition);
            }
            con.close();
            return oppositions;
        } catch (Exception ex) {
            System.out.println("error al obtener oposiciones tramite " + searchedApplicationNumber + ": " + ex);
            return new ArrayList<>();
        }
    }

    public List<OppositionForms> getOppositionFormsByApplicationNumber(String applicationNumber) {
        String query = "Select * from opposition_forms as of "
                + "INNER JOIN iepi_casilleros.owners as ow ON ow.id = of.owner_id "
                + "INNER JOIN iepi_casilleros.lockers as lo ON lo.owner_id = ow.id "
                + "where of.application_number = '" + applicationNumber + "'";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            List<OppositionForms> oppositions = new ArrayList<>();
            while (rs.next()) {
                OppositionForms opposition = new OppositionForms();
                Timestamp taux = rs.getTimestamp("application_date");
                if (taux != null) {
                    opposition.setApplicationDate(Operaciones.formatTimesTamp(taux));
                }
                opposition.setApplicationNumber(rs.getString("application_number"));
                opposition.setFormId(rs.getInt("form_id"));
                opposition.setExpedient(rs.getString("opposition_application_number"));
                opposition.setId(rs.getInt("id"));
                opposition.setOwnerId(rs.getInt("owner_id"));
                opposition.setPaymentReceiptId(rs.getInt("payment_receipt_id"));

                opposition.setPowerAttorney(rs.getString("power_attorney"));
                opposition.setSearchClue(rs.getString("search_clue"));
                opposition.setSearchedApplicationNumber(rs.getString("searched_application_number"));
                opposition.setStatus(rs.getString("status"));
                opposition.setCasillero(rs.getInt("lo.id") + "");

                oppositions.add(opposition);
            }
            con.close();
            return oppositions;
        } catch (Exception ex) {
            System.out.println("error al obtener oposiciones tramite " + applicationNumber + ": " + ex);
            return new ArrayList<>();
        }
    }

    public List<VoucherForm> getVoucherFormByAppOrDocNumber(String appordoc, boolean avisa) {
        String query;
        String cas = "LEFT JOIN iepi_casilleros.owners as ow ON ow.id = v.owner_id "
                + "LEFT JOIN iepi_casilleros.lockers as lo ON lo.owner_id = ow.id ";
        if (avisa) {
            query = "Select * from voucher as v "
                    + cas
                    + "where v.application_number = '" + appordoc + "'";
        } else {
            query = "Select * from voucher as v "
                    + cas
                    + "where v.document_number = '" + appordoc + "'";
        }

        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            List<VoucherForm> vouchers = new ArrayList<>();
            while (rs.next()) {
                VoucherForm voucher = new VoucherForm();
                Timestamp taux = rs.getTimestamp("application_date");
                if (taux != null) {
                    voucher.setApplicationDate(Operaciones.formatTimesTamp(taux));
                }
                voucher.setApplicationNumber(rs.getString("application_number"));
                voucher.setDocumentNumber(rs.getString("document_number"));
                voucher.setId(rs.getInt("id"));
                voucher.setObservacion(rs.getString("observacion"));
                voucher.setOwnerId(rs.getInt("owner_id"));
                voucher.setPaymentReceiptId(rs.getInt("payment_receipt_id"));
                voucher.setPersonId(rs.getInt("person_id"));
                voucher.setValue(rs.getDouble("value"));
                voucher.setDelivered(rs.getBoolean("delivered"));

                voucher.setCasillero(rs.getInt("lo.id") + "");

                vouchers.add(voucher);
            }
            con.close();
            return vouchers;
        } catch (Exception ex) {
            System.out.println("error al obtener oposiciones tramite " + appordoc + ": " + ex);
            return new ArrayList<>();
        }
    }

    public PlayForm getPlayFormByApplicationNumber(String applicationNumber) {
        String query = "Select * from play_forms as pf "
                + "inner join forms as f on f.id = pf.play_type_id "
                + "INNER JOIN iepi_casilleros.owners as ow ON ow.id = pf.owner_id "
                + "INNER JOIN iepi_casilleros.lockers as lo ON lo.owner_id = ow.id "
                + "where pf.application_number = '" + applicationNumber + "'";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            PlayForm playForm = new PlayForm();
            while (rs.next()) {
                Timestamp taux = rs.getTimestamp("application_date");
                if (taux != null) {
                    playForm.setApplicationDate(Operaciones.formatTimesTamp(taux));
                }
                playForm.setApplicationNumber(rs.getString("application_number"));
                playForm.setDescription(rs.getString("description"));
                playForm.setId(rs.getInt("id"));
                playForm.setOwnerId(rs.getInt("owner_id"));
                playForm.setPaymentReceiptId(rs.getInt("payment_receipt_id"));
                playForm.setPlayTypeId(rs.getInt("play_type_id"));
                playForm.setStatus(rs.getString("status"));
                playForm.setSynopsis(rs.getString("synopsis"));
                playForm.setTitle(rs.getString("title"));
                playForm.setTitleOriginal(rs.getString("title_original"));
                playForm.setTitleTranslated(rs.getString("title_translated"));
                playForm.setTipo(rs.getString("name"));
                playForm.setAlias(rs.getString("alias"));
                playForm.setCasillero(rs.getInt("lo.id") + "");
            }
            con.close();
            return playForm;
        } catch (Exception ex) {
            System.out.println("error al obtener playforms del tramite " + applicationNumber + ": " + ex);
            return new PlayForm();
        }
    }

    public PatentForms getPatentFormByApplicationNumber(String applicationNumber) {
        String query = "Select * from patent_forms as pf "
                + "inner join form_types as f on f.id = pf.patent_type_id "
                + "inner join types as t on t.id = f.type_id "
                + "INNER JOIN iepi_casilleros.owners as ow ON ow.id = pf.owner_id "
                + "INNER JOIN iepi_casilleros.lockers as lo ON lo.owner_id = ow.id "
                + "where application_number = '" + applicationNumber + "'";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            PatentForms patentForm = new PatentForms();
            while (rs.next()) {
                Timestamp taux = rs.getTimestamp("application_date");
                if (taux != null) {
                    patentForm.setApplicationDate(Operaciones.formatTimesTamp(taux));
                }
                patentForm.setApplicationNumber(rs.getString("application_number"));
                patentForm.setClaims(rs.getInt("claims"));
                patentForm.setExpedient(rs.getString("expedient"));
                patentForm.setId(rs.getInt("id"));
                patentForm.setInternationalClassification(rs.getString("international_classification"));
                patentForm.setOwnerId(rs.getInt("owner_id"));
                patentForm.setPatentTypeId(rs.getInt("patent_type_id"));
                patentForm.setPaymentReceitpId(rs.getInt("payment_receipt_id"));
                patentForm.setStatus(rs.getString("status"));
                patentForm.setSummary(rs.getString("summary"));
                patentForm.setTipo(rs.getString("name"));
                patentForm.setTitle(rs.getString("title"));
                patentForm.setImage(rs.getString("image"));

                patentForm.setCasillero(rs.getInt("lo.id") + "");

            }
            con.close();
            return patentForm;
        } catch (Exception ex) {
            System.out.println("error al obtener playforms del tramite " + applicationNumber + ": " + ex);
            return new PatentForms();
        }
    }

    public RenewalForm getRenewalFormByApplicationNumber(String applicationNumber) {
        String query = "Select rf.application_date, rf.application_number, rf.branch_office, rf.create_date, rf.debug_date, rf.debug_id, "
                + "rf.debug_table_name, rf.denomination, rf.expedient, rf.id, rf.license_type, rf.owner_id, rf.power_attorney, rf.status, "
                + "rf.search_clue, t.name as tipo, rf.payment_receipt_id, lo.id as casillero "
                + "from renewal_forms as rf "
                + "inner join form_types as ft on ft.id = rf.transaction_motive_id "
                + "inner join forms as f on f.id = ft.form_id "
                + "inner join types as t on t.id = ft.type_id "
                + "INNER JOIN iepi_casilleros.owners as ow ON ow.id = rf.owner_id "
                + "INNER JOIN iepi_casilleros.lockers as lo ON lo.owner_id = ow.id "
                + "where rf.application_number = '" + applicationNumber + "'";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            RenewalForm renewalForm = new RenewalForm();
            while (rs.next()) {
                Timestamp taux = rs.getTimestamp("application_date");
                if (taux != null) {
                    renewalForm.setApplicationDate(Operaciones.formatTimesTamp(taux));
                }
                renewalForm.setApplicationNumber(rs.getString("application_number"));
                renewalForm.setBranchOffice(rs.getString("branch_office"));
                renewalForm.setCreateDate(rs.getTimestamp("create_date"));
                renewalForm.setDebugDate(rs.getDate("debug_date"));
                renewalForm.setDebugId(rs.getInt("debug_id"));
                renewalForm.setDebugTableName(rs.getString("debug_table_name"));
                renewalForm.setDenomination(rs.getString("denomination"));
                renewalForm.setExpedient(rs.getString("expedient"));
                renewalForm.setId(rs.getInt("id"));
                renewalForm.setLicenseType(rs.getString("license_type"));
                renewalForm.setOwnerId(rs.getInt("owner_id"));
                renewalForm.setPowerAttorney(rs.getString("power_attorney"));
                renewalForm.setSearchClue(rs.getString("search_clue"));
                renewalForm.setTipo(rs.getString("tipo"));
                renewalForm.setStatus(rs.getString("status"));
                renewalForm.setPaymentReceiptId(rs.getInt("payment_receipt_id"));

                renewalForm.setCasillero(rs.getInt("casillero") + "");
            }
            con.close();
            return renewalForm;
        } catch (Exception ex) {
            System.out.println("error al obtener renewalforms del tramite " + applicationNumber + ": " + ex);
            return new RenewalForm();
        }
    }

    public RenewalForm getRenewalFormByDebugId(Integer debugId) {
        String query = "Select rf.application_date, rf.application_number, rf.branch_office, rf.create_date, rf.debug_date, rf.debug_id, "
                + "rf.debug_table_name, rf.denomination, rf.expedient, rf.id, rf.license_type, rf.owner_id, rf.power_attorney, rf.status, "
                + "rf.search_clue, t.name as tipo, rf.payment_receipt_id, lo.id as casillero "
                + "from renewal_forms as rf "
                + "inner join form_types as ft on ft.id = rf.transaction_motive_id "
                + "inner join forms as f on f.id = ft.form_id "
                + "inner join types as t on t.id = ft.type_id "
                + "INNER JOIN iepi_casilleros.owners as ow ON ow.id = rf.owner_id "
                + "INNER JOIN iepi_casilleros.lockers as lo ON lo.owner_id = ow.id "
                + "where rf.debug_id = " + debugId;
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            RenewalForm renewalForm = new RenewalForm();
            while (rs.next()) {
                Timestamp taux = rs.getTimestamp("application_date");
                if (taux != null) {
                    renewalForm.setApplicationDate(Operaciones.formatTimesTamp(taux));
                }
                renewalForm.setApplicationNumber(rs.getString("application_number"));
                renewalForm.setBranchOffice(rs.getString("branch_office"));
                renewalForm.setCreateDate(rs.getTimestamp("create_date"));
                renewalForm.setDebugDate(rs.getDate("debug_date"));
                renewalForm.setDebugId(rs.getInt("debug_id"));
                renewalForm.setDebugTableName(rs.getString("debug_table_name"));
                renewalForm.setDenomination(rs.getString("denomination"));
                renewalForm.setExpedient(rs.getString("expedient"));
                renewalForm.setId(rs.getInt("id"));
                renewalForm.setLicenseType(rs.getString("license_type"));
                renewalForm.setOwnerId(rs.getInt("owner_id"));
                renewalForm.setPowerAttorney(rs.getString("power_attorney"));
                renewalForm.setSearchClue(rs.getString("search_clue"));
                renewalForm.setTipo(rs.getString("tipo"));
                renewalForm.setStatus(rs.getString("status"));
                renewalForm.setPaymentReceiptId(rs.getInt("payment_receipt_id"));

                renewalForm.setCasillero(rs.getInt("casillero") + "");
            }
            con.close();
            return renewalForm;
        } catch (Exception ex) {
            System.out.println("error al obtener renewalforms del tramite " + debugId + ": " + ex);
            return new RenewalForm();
        }
    }

    public String getBreederNombreArchivo(String archivo) {
        String query = "SELECT va.name FROM "
                + "vegetable_annexes_data as vad "
                + "INNER JOIN vegetable_annexes AS va ON va.id = vad.vegetable_annexes_id "
                + "WHERE vad.file_name = '" + archivo + "'";
        try {
            Connection con = ParametrosBD.doConnectionToVegetable();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            String nombre = "";
            while (rs.next()) {
                nombre = rs.getString("name");
            }
            con.close();
            return nombre;
        } catch (SQLException ex) {
            System.out.println("error al obtener vegetable nombre documento " + archivo + ": " + ex);
            return "";
        }
    }

    public FileAnnex getDenominationFile(String archivo) {
        String query = "SELECT * FROM "
                + "denomination_annexes_data as dad "
                + "INNER JOIN denomination_annexes AS da ON da.id = dad.denomination_annex_id "
                + "WHERE dad.file = '" + archivo + "'";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            FileAnnex file = new FileAnnex();
            while (rs.next()) {
                file.setApplicationId(rs.getInt("dad.denomination_form_id"));
                file.setAnnexId(rs.getInt("dad.denomination_annex_id"));
                file.setFile(rs.getString("dad.file"));
                file.setFileDescription(rs.getString("dad.file_description"));
                file.setUserUpload(rs.getString("dad.user_upload"));
                file.setUploadDate(rs.getTimestamp("dad.upload_date"));
                file.setDocumentName(rs.getString("da.name"));
            }
            con.close();
            return file;
        } catch (SQLException ex) {
            System.out.println("error al obtener denomination nombre documento " + archivo + ": " + ex);
            return new FileAnnex();
        }
    }

    public FileAnnexesApplication getFileAnnexesApplication(String applicationNumber, String fileName, String applicationType) {
        String query = "SELECT * FROM "
                + "file_annexes_application "
                + "WHERE application_number = '"+applicationNumber+"' "
                + "and file_name = '" + fileName + "' "
                + "and application_type = '" + applicationType + "' "
                + "and file_status = 'UPLOAD'";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            FileAnnexesApplication file = new FileAnnexesApplication();
            while (rs.next()) {
                file.setId(rs.getInt("id"));
                file.setFileName(rs.getString("file_name"));
                file.setFileDescription(rs.getString("file_description"));
                file.setUserUpload(rs.getString("user_upload"));
                file.setUploadDate(rs.getTimestamp("upload_date"));
                file.setFileStatus(rs.getString("file_status"));
                file.setApplicationType(rs.getString("application_type"));
                file.setUserUpdate(rs.getString("user_update"));
                file.setUpdateDate(rs.getTimestamp("update_date"));
            }
            con.close();
            return file;
        } catch (SQLException ex) {
            System.out.println("error al obtener file_annexes_application " + fileName + ": " + ex);
            return new FileAnnexesApplication();
        }
    }

    public Integer getVegetableFormsId(String applicationNumber) {
        String query = "SELECT id FROM vegetable_forms "
                + "WHERE application_number = '" + applicationNumber + "'";
        try {
            Connection con = ParametrosBD.doConnectionToVegetable();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Integer id = 0;
            while (rs.next()) {
                id = rs.getInt("id");
            }
            con.close();
            return id;
        } catch (SQLException ex) {
            System.out.println("error al obtener el id de vegetable_forms " + applicationNumber + ": " + ex);
            return 0;
        }
    }

    public String getPatentNombreArchivo(String archivo) {
        String query = "SELECT pa.name FROM "
                + "patent_annexes_data AS pad "
                + "INNER JOIN patent_annexes AS pa ON pa.id = pad.patent_annex_id "
                + "WHERE pad.file = '" + archivo + "'";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            String nombre = "";
            while (rs.next()) {
                nombre = rs.getString("name");
            }
            con.close();
            return nombre;
        } catch (Exception ex) {
            System.out.println("error al obtener patent nombre documento " + archivo + ": " + ex);
            return "";
        }
    }

    public String getScopeNombreArchivo(String archivo) {
        String query = "SELECT pa.name FROM "
                + "scope_annexes_data AS pad "
                + "INNER JOIN scope_annexes AS pa ON pa.id = pad.scope_annex_id "
                + "WHERE pad.file = '" + archivo + "'";

        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            String nombre = "";
            while (rs.next()) {
                nombre = rs.getString("name");
            }
            con.close();
            return nombre;
        } catch (Exception ex) {
            System.out.println("error al obtener nombre scope documento " + archivo + ": " + ex);
            return "";
        }
    }

    public String getOppositionNombreArchivo(String archivo) {
        String query = "SELECT pa.name FROM "
                + "opposition_annexes_data AS pad "
                + "INNER JOIN opposition_annexes AS pa ON pa.id = pad.opposition_annex_id "
                + "WHERE pad.file = '" + archivo + "'";

        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            String nombre = "";
            while (rs.next()) {
                nombre = rs.getString("name");
            }
            con.close();
            return nombre;
        } catch (Exception ex) {
            System.out.println("error al obtener nombre oposición documento " + archivo + ": " + ex);
            return "";
        }
    }

    public String getPlayNombreArchivo(String archivo) {
        String query = "SELECT pa.name FROM "
                + "play_annexes_data AS pad "
                + "INNER JOIN play_annexes AS pa ON pa.id = pad.play_annex_id "
                + "WHERE pad.file = '" + archivo + "'";

        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            String nombre = "";
            while (rs.next()) {
                nombre = rs.getString("name");
            }
            con.close();
            return nombre;
        } catch (Exception ex) {
            System.out.println("error al obtener nombre play documento " + archivo + ": " + ex);
            return "";
        }
    }

    public String getHallmarkNombreArchivo(String archivo) {
        String query = "SELECT pa.name FROM "
                + "hallmark_annexes_data AS pad "
                + "INNER JOIN hallmark_annexes AS pa ON pa.id = pad.hallmark_annex_id "
                + "WHERE pad.file = '" + archivo + "'";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            String nombre = "";
            while (rs.next()) {
                nombre = rs.getString("name");
            }
            con.close();
            return nombre;
        } catch (Exception ex) {
            System.out.println("error al obtener hallmark nombre documento " + archivo + ": " + ex);
            return "";
        }
    }

    public String getTutelageNombreArchivo(String archivo) {
        String query = "SELECT pa.name FROM "
                + "tutelage_annexes_data AS pad "
                + "INNER JOIN tutelage_annexes AS pa ON pa.id = pad.tutelage_annex_id "
                + "WHERE pad.file = '" + archivo + "'";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            String nombre = "";
            while (rs.next()) {
                nombre = rs.getString("name");
            }
            con.close();
            return nombre;
        } catch (Exception ex) {
            System.out.println("error al obtener tutelage nombre documento " + archivo + ": " + ex);
            return "";
        }
    }

    public String getTutelageSampleNombreArchivo(String archivo) {
        String query = "SELECT ts.name FROM "
                + "tutelage_samples AS ts "
                + "WHERE ts.file = '" + archivo + "'";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            String nombre = "";
            while (rs.next()) {
                nombre = rs.getString("name");
            }
            con.close();
            return nombre;
        } catch (Exception ex) {
            System.out.println("error al obtener tutelage sample nombre documento " + archivo + ": " + ex);
            return "";
        }
    }

    public String getRenewalNombreArchivo(String archivo) {
        String query = "SELECT pa.name FROM "
                + "renewal_annexes_data AS pad "
                + "INNER JOIN renewal_annexes AS pa ON pa.id = pad.renewal_annex_id "
                + "WHERE pad.file = '" + archivo + "'";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            String nombre = "";
            while (rs.next()) {
                nombre = rs.getString("name");
            }
            con.close();
            return nombre;
        } catch (Exception ex) {
            System.out.println("error al obtener renewal nombre documento " + archivo + ": " + ex);
            return "";
        }
    }

    public boolean updateOwnerHallmark(HallmarkForm hallmark) {
        String query = "UPDATE hallmark_forms set owner_id = " + hallmark.getOwnerId() + " where application_number = '" + hallmark.getApplicationNumber() + "' "
                + "and id = " + hallmark.getId();
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            int n = pst.executeUpdate();
            con.close();
            return n > 0;
        } catch (SQLException se) {
            System.out.println("Error al actualizar el owner id de la marca: " + hallmark.getApplicationNumber());
            return false;
        }
    }

    public String getApplicationType(String applicationNumber) {
        String query = "Select * from applications where application_number = '" + applicationNumber + "'";
        try {
            Connection con = ParametrosBD.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            String application_type = "";
            while (rs.next()) {
                application_type = rs.getString("table_name");
                break;
            }
            con.close();
            return application_type;
        } catch (SQLException ex) {
            System.err.println("No se pudo encontrar el tipo del trámite: " + applicationNumber + ": " + ex);
            return "";
        }
    }
}
