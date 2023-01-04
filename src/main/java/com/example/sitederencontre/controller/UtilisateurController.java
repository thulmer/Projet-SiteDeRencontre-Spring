package com.example.sitederencontre.controller;

import com.example.sitederencontre.entities.Interaction;
import com.example.sitederencontre.entities.Match;
import com.example.sitederencontre.entities.Message;
import com.example.sitederencontre.entities.Utilisateur;
import com.example.sitederencontre.exportpdf.UtilisateurPdfExporter;
import com.example.sitederencontre.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class UtilisateurController {
    @Autowired
    UtilisateurService service;
    @Autowired
    InteractionService serviceInter;
    @Autowired
    MatchService serviceMatch;
    @Autowired
    private EmailService emailService;

    //----------------------------------------------------------------------------------------------------------------
    //AFFICHER LES UTILISATEURS
    //----------------------------------------------------------------------------------------------------------------

    @GetMapping("/utilisateurs/liste")
    public String afficherUtilisateurListe(Model model, HttpSession session, @RequestParam(required = false) String localisation, @RequestParam(required = false) Integer age) {
        Utilisateur uti = (Utilisateur) session.getAttribute("utilisateur");
        if (localisation != null) {
            session.setAttribute("limiteLocalisation", localisation);
        }else{
            session.setAttribute("limiteLocalisation", null);
        }
        if (age != null) {
            session.setAttribute("limiteAge", age);
        }
        List<Utilisateur> listUtilisateurs = null;
        if (uti.isAdmin()) {
            listUtilisateurs = service.listAll();
        } else {
            listUtilisateurs = service.listPasEncoreVus(uti, (Integer) session.getAttribute("limiteAge"), (String) session.getAttribute("limiteLocalisation"));
        }
        List<String> listLocalisations = service.afficherLocalisations();
        model.addAttribute("listUtilisateurs", listUtilisateurs);
        model.addAttribute("listLocalisations", listLocalisations);

        if (listUtilisateurs.isEmpty()) {
            model.addAttribute("message", "Vous avez vu tout le monde, revenez plus tard!");
        }
        return "utilisateurs-liste";
    }

    @GetMapping("/utilisateurs/swipe")
    public String afficherUtilisateurSwipe(Model model, HttpSession session, @RequestParam(required = false) String localisation, @RequestParam(required = false) Integer age) {
        if (session.getAttribute("status") != null) {
            Utilisateur uti = (Utilisateur) session.getAttribute("utilisateur");
            if (localisation != null) {
                session.setAttribute("limiteLocalisation", localisation);
            }
            if (age != null) {
                session.setAttribute("limiteAge", age);
            }
            List<Utilisateur> listUtilisateurs = service.listPasEncoreVus(uti, (Integer) session.getAttribute("limiteAge"), (String) session.getAttribute("limiteLocalisation"));
            List<String> listLocalisations = service.afficherLocalisations();
            model.addAttribute("listUtilisateurs", listUtilisateurs);
            model.addAttribute("listLocalisations", listLocalisations);

            if (listUtilisateurs.isEmpty()) {
                model.addAttribute("message", "Vous avez vu tout le monde, revenez plus tard!");
            }
        } else {
            List<Utilisateur> listUtilisateurs = service.listAll();
            model.addAttribute("listUtilisateurs", listUtilisateurs);
        }
        return "utilisateurs-swipe";
    }

    //----------------------------------------------------------------------------------------------------------------
    //INTERACTIONS ENTRE UTILISATEURS
    //----------------------------------------------------------------------------------------------------------------

    @GetMapping("/utilisateurs/{vue}/dislike/{id}")
    public String dislikeUtilisateur(@PathVariable(name = "id") Integer idSwiped,
                                     @PathVariable(name = "vue") String vue,
                                     HttpSession session) {
        Utilisateur swiper = (Utilisateur) session.getAttribute("utilisateur");
        Utilisateur swiped = service.get(idSwiped);
        Interaction inter = new Interaction(swiper, swiped, false);
        serviceInter.save(inter);

        return "redirect:/utilisateurs/{vue}";
    }

    @GetMapping("/utilisateurs/{vue}/like/{id}")
    public String likeUtilisateur(@PathVariable(name = "id") Integer idSwiped,
                                  @PathVariable(name = "vue") String vue,
                                  HttpSession session, RedirectAttributes redirectAttributes) throws MessagingException {
        Utilisateur swiper = (Utilisateur) session.getAttribute("utilisateur");
        Utilisateur swiped = service.get(idSwiped);
        Interaction inter = new Interaction(swiper, swiped, true);
        serviceInter.save(inter);

        boolean likeMutuel = serviceInter.rechercherInteractionParIds(swiped, swiper);
        if (likeMutuel) {
            serviceMatch.save(new Match(swiper, swiped));
            serviceMatch.save(new Match(swiped, swiper));
            if(swiped.isRecevoirEmails()) {
                emailService.sendMail(swiped.getEmail(), "Luvmo - Vous avez un nouveau match", "<html><body><h1>" + swiped.getPrenom() + " vous avez matché avec " + swiper.getPrenom() + "</h1>\n<p>Connectez vous pour discuter : <a href='http://localhost:8080/connexion')>Connexion</a></p></body></html>");
            }            redirectAttributes.addFlashAttribute("message", "C'est un match! Vous pouvez discuter avec " + swiped.getPrenom() + ".");
        }
        return "redirect:/utilisateurs/{vue}";
    }

    @GetMapping("/utilisateurs/{vue}/superlike/{id}")
    public String superlikeUtilisateur(@PathVariable(name = "id") Integer idSwiped,
                                       @PathVariable(name = "vue") String vue,
                                       HttpSession session, RedirectAttributes redirectAttributes) throws MessagingException {
        Utilisateur swiper = (Utilisateur) session.getAttribute("utilisateur");
        Utilisateur swiped = service.get(idSwiped);
        if (!swiped.isAdmin()) {
            Interaction inter = new Interaction(swiper, swiped, true);
            Interaction inter2 = new Interaction(swiped, swiper, true);
            if (session.getAttribute("status") == "Privilège") {
                serviceInter.save(inter);
                serviceInter.save(inter2);
                serviceMatch.save(new Match(swiper, swiped));
                serviceMatch.save(new Match(swiped, swiper));
                if(swiped.isRecevoirEmails()) {
                    emailService.sendMail(swiped.getEmail(), "Luvmo - Vous avez un nouveau match", "<html><body><h1>" + swiped.getPrenom() + " vous avez matché avec " + swiper.getPrenom() + "</h1>\n<p>Connectez vous pour discuter : <a href='http://localhost:8080/connexion')>Connexion</a></p></body></html>");
                }
                redirectAttributes.addFlashAttribute("message", "C'est un match! Vous pouvez discuter avec " + swiped.getPrenom() + ".");

            }
        }
        return "redirect:/utilisateurs/{vue}";
    }

    //----------------------------------------------------------------------------------------------------------------
    //PROFIL ET GESTION DE PROFIL
    //----------------------------------------------------------------------------------------------------------------

    @GetMapping("/profil")
    public String afficherProfil(Model model, HttpSession session) {
        model.addAttribute("utilisateur", session.getAttribute("utilisateur"));
        model.addAttribute("pageTitle", "Mon profil");
        return "profil";
    }

    @PostMapping("/profil")
    public String modifierUtilisateur(Utilisateur utilisateur, RedirectAttributes redirectAttributes, @RequestParam(value = "fileImage", required = false) MultipartFile multipartFile, HttpSession session) {

        if (multipartFile != null && !multipartFile.isEmpty()) {
            Path root = Paths.get("src/main/resources/static/images/utilisateurs");
            String chemin = multipartFile.getOriginalFilename();
            String fileName = StringUtils.cleanPath(chemin);
            String extension = fileName.substring(fileName.lastIndexOf("."));
            String nouveauFileName = utilisateur.getPrenom() + String.valueOf(utilisateur.getId()) + extension;
            if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".jpeg")) {
                utilisateur.setImgLink(nouveauFileName);
                try {
                    Files.copy(multipartFile.getInputStream(), root.resolve(nouveauFileName), StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("messageErreur", "Votre image n'a pas été téléchargée.");
                }
            } else {
                redirectAttributes.addFlashAttribute("messageErreur", "Votre image n'a pas été téléchargée. Elle doit être au format JPG ou PNG.");
            }
        }
        redirectAttributes.addFlashAttribute("message", "Modification réussie.");
        service.save(utilisateur);
        if (session.getAttribute("status") == "Admin") {
            return "redirect:/utilisateurs/liste";
        }
        session.setAttribute("utilisateur", utilisateur);
        return "redirect:/profil";
    }

    //----------------------------------------------------------------------------------------------------------------
    //INSCRIPTION, CONNEXION ET DECONNEXION
    //----------------------------------------------------------------------------------------------------------------

    @GetMapping("/inscription")
    public String formulaireInscription(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("pageTitle", "Inscription");
        return "inscription";
    }

    //Fonction qui gère l'inscription d'un utilisateur ET l'ajout d'un utilisateur par l'admin
    @PostMapping("/inscription")
    public String ajouterUtilisateur(Utilisateur utilisateur, RedirectAttributes redirectAttributes, @RequestParam(value = "fileImage", required = false) MultipartFile multipartFile, HttpSession session) {

        if (multipartFile != null && !multipartFile.isEmpty()) {
            Path root = Paths.get("src/main/resources/static/images/utilisateurs");
            String chemin = multipartFile.getOriginalFilename();
            String fileName = StringUtils.cleanPath(chemin);
            String extension = fileName.substring(fileName.lastIndexOf("."));
            Integer lastId = service.getLastId();
            String nouveauFileName = utilisateur.getPrenom() + String.valueOf(lastId + 1) + extension;
            if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".jpeg")) {
                utilisateur.setImgLink(nouveauFileName);
                try {
                    Files.copy(multipartFile.getInputStream(), root.resolve(nouveauFileName), StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("messageErreur", "Votre image n'a pas été téléchargée.");
                    utilisateur.setImgLink("default.png");
                }
            }
        } else {
            utilisateur.setImgLink("default.png");
        }
        redirectAttributes.addFlashAttribute("message", "Inscription réussie.");
        service.save(utilisateur);
        if (session.getAttribute("status") == "Admin") {
            return "redirect:/utilisateurs/liste";
        }
        return "redirect:/connexion";
    }

    @GetMapping("/connexion")
    public String formulaireConnexion(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "login";
    }

    @PostMapping("/connexion")
    public String connexionUtilisateur(Utilisateur utilisateur, HttpServletRequest request) {
        Utilisateur utilisateurConnecte = service.findParEmail(utilisateur.getEmail());
        service.updateEnLigne(utilisateurConnecte.getId(), true);
        if (utilisateurConnecte.isAdmin()) {
            request.getSession().setAttribute("status", "Admin");
        } else if (utilisateurConnecte.isPrivilege()) {
            request.getSession().setAttribute("status", "Privilège");
        } else {
            request.getSession().setAttribute("status", "Utilisateur");
        }
        request.getSession().setAttribute("utilisateur", utilisateurConnecte);
        return "redirect:/";

    }

    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request, HttpSession session) {
        Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
        if (user != null) {
            service.updateEnLigne(user.getId(), false);
        }
        request.getSession().invalidate();
        return "redirect:/";
    }

    //----------------------------------------------------------------------------------------------------------------
    //ADMINISTRATION
    //----------------------------------------------------------------------------------------------------------------

    @GetMapping("/utilisateurs/new")
    public String formulaireAjout(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("pageTitle", "Ajouter un utilisateur");
        return "inscription";
    }

    @GetMapping("/utilisateurs/modifier/{id}")
    public String MettreAjourUtilisateur(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {
        if (session.getAttribute("status") == "Admin") {
            Utilisateur utilisateur = service.get(id);
            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("pageTitle", "Editer l'utilisateur (ID: " + id + ")");
        } else {
            return "redirect:/";
        }
        return "profil";
    }

    @GetMapping("/utilisateurs/delete/{id}")
    public String supprimerUtilisateur(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes, HttpSession session) throws MessagingException {
        if (session.getAttribute("status") == "Admin") {
            Utilisateur user = service.get(id);
            emailService.sendMail(user.getEmail(),"Luvmo - Suppression de votre compte","<html><body><h1>" + user.getPrenom() + " nous avons supprimé votre compte.</h1></body></html>");
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "L'utilisateur ID " + id + " a été supprimé avec succès ");
        } else {
            return "redirect:/";
        }
        return "redirect:/utilisateurs/liste";
    }

    @GetMapping("/utilisateurs/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException, IOException {
        List<Utilisateur> listUsers = service.listAll();

        UtilisateurPdfExporter exporter = new UtilisateurPdfExporter();
        exporter.export(listUsers, response);
    }

}
