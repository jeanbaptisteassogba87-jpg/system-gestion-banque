import java.util.ArrayList;
import java.util.List;
public class CompteBancaire {
        protected String titulaire ;
        protected Double solde ;
        protected String numeroCompte ;

        CompteBancaire(String titulaire , Double solde , String numeroCompte){
            this.titulaire = titulaire ;
            this.solde = solde ;
            this.numeroCompte = numeroCompte ;
        }

        // Getters

        public String getTitulaire(){
            return titulaire ;
        }
        
        public Double getSolde(){
            return solde ;
        }

        public String getNumeroCompte(){
            return numeroCompte ;
        }

        // Setters

        public void setTitulaire(String titulaire){
            this.titulaire = titulaire ;
        }

        public void setSolde(Double solde){
            this.solde = solde ;
        }

        public void setNumeroCompte(String numeroCompte){
            this.numeroCompte = numeroCompte ;
        }


        public void deposer(Double montant){
            if (montant > 0) {
                solde += montant ;
                System.out.println("Montant ajouté avec succès !");
            }else{
                System.out.println("Echec ! \t Montant doit être supérieur à zéro !");
            }
        }

        public void retirer(Double montant){
            if(montant <= solde  && montant > 0 ){
                solde -= montant ;
                System.out.println("Montant retiré avec succès !");
            }else{
                System.out.println("Echec ! \t Montant invalide  !");
            }
        }

        public void afficherInfos(){
            System.out.println("Titulaire : " + titulaire + " Numéro Compte : " + numeroCompte + " Solede :" + solde );
        }


    public static void main(String[] args) {
        System.out.println("=== SYSTÈME DE GESTION BANCAIRE ===\n");
        
        // 1. CRÉATION DE LA BANQUE
        Banque maBanque = new Banque();
        
        // 2. CRÉATION DE DIFFÉRENTS COMPTES
        System.out.println("--- CRÉATION DES COMPTES ---");
        
        CompteCourant compteAlice = new CompteCourant("Alice", 1000.0, "FR001", 500.0);
        CompteEpargne compteBob = new CompteEpargne("Bob", 2000.0, "FR002", 0.05);
        CompteCourant compteCharlie = new CompteCourant("Charlie", 500.0, "FR003", 200.0);
        CompteEpargne compteDiana = new CompteEpargne("Diana", 3000.0, "FR004", 0.03);
        
        // 3. AJOUT DES COMPTES À LA BANQUE
        System.out.println("\n--- AJOUT DES COMPTES À LA BANQUE ---");
        maBanque.ajouterCompte(compteAlice);
        maBanque.ajouterCompte(compteBob);
        maBanque.ajouterCompte(compteCharlie);
        maBanque.ajouterCompte(compteDiana);
        
        // 4. AFFICHAGE DE TOUS LES COMPTES
        System.out.println("\n--- ÉTAT INITIAL DES COMPTES ---");
        maBanque.afficherTouslesComptes();
        
        // 5. TEST DES OPÉRATIONS SUR COMPTE COURANT
        System.out.println("\n--- TEST COMPTE COURANT (Alice) ---");
        compteAlice.afficherInfos();
        
        // Dépôt
        System.out.println("\nDépôt de 200 € :");
        compteAlice.deposer(200.0);
        compteAlice.afficherInfos();
        
        // Retrait normal
        System.out.println("\nRetrait de 300 € :");
        compteAlice.retirer(300.0);
        compteAlice.afficherInfos();
        
        // Retrait avec découvert (dans la limite)
        System.out.println("\nRetrait de 800 € (avec découvert) :");
        compteAlice.retirer(800.0);
        compteAlice.afficherInfos();
        
        // Retrait dépassant le découvert
        System.out.println("\nTentative de retrait de 1000 € (dépasse découvert) :");
        compteAlice.retirer(1000.0);
        compteAlice.afficherInfos();
        
        // 6. TEST DES OPÉRATIONS SUR COMPTE ÉPARGNE
        System.out.println("\n--- TEST COMPTE ÉPARGNE (Bob) ---");
        compteBob.afficherInfos();
        
        // Application des intérêts
        System.out.println("\nApplication des intérêts (taux: 5%) :");
        compteBob.appliquerInteret();
        compteBob.afficherInfos();
        
        // 7. TEST DES TRANSFERTS
        System.out.println("\n--- TEST DES TRANSFERTS ---");
        System.out.println("Avant transfert :");
        compteAlice.afficherInfos();
        compteBob.afficherInfos();
        
        System.out.println("\nTransfert de 150 € de Alice (FR001) vers Bob (FR002) :");
        maBanque.transferer("FR001", "FR002", 150.0);
        
        System.out.println("\nAprès transfert :");
        compteAlice.afficherInfos();
        compteBob.afficherInfos();
        
        // 8. TEST DES CAS D'ERREUR
        System.out.println("\n--- TEST DES CAS D'ERREUR ---");
        
        // Transfert vers compte inexistant
        System.out.println("Transfert vers compte inexistant (FR999) :");
        maBanque.transferer("FR001", "FR999", 100.0);
        
        // Montant négatif
        System.out.println("\nDépôt de montant négatif :");
        compteCharlie.deposer(-50.0);
        
        // Retrait avec montant négatif
        System.out.println("\nRetrait avec montant négatif :");
        compteCharlie.retirer(-30.0);
        
        // 9. AFFICHAGE FINAL
        System.out.println("\n--- ÉTAT FINAL DES COMPTES ---");
        maBanque.afficherTouslesComptes();
        
        // 10. DÉMONSTRATION DU POLYMORPHISME
        System.out.println("\n--- DÉMONSTRATION DU POLYMORPHISME ---");
        System.out.println("Parcours de tous les comptes avec une boucle :");
        for (CompteBancaire compte : maBanque.getComptes()) {
            // Le bon type de compte s'affiche automatiquement !
            if (compte instanceof CompteEpargne) {
                System.out.print("[COMPTE ÉPARGNE] ");
            } else if (compte instanceof CompteCourant) {
                System.out.print("[COMPTE COURANT] ");
            }
            compte.afficherInfos();
        }
    }
}

class CompteEpargne extends CompteBancaire{
    private Double tauxInteret ;

    CompteEpargne(String titulaire , Double solde , String numeroCompte , Double tauxInteret ){
        super(titulaire, solde, numeroCompte);
        this.tauxInteret = tauxInteret ;
    }

    public Double getTauxInteret() {
        return tauxInteret;
    }
    
    public void setTauxInteret(Double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    public void appliquerInteret(){
        solde = solde + (solde * tauxInteret) ;
    }
}

class CompteCourant extends CompteBancaire{
    private Double decouvertAutorise ;

    CompteCourant(String titulaire , Double solde , String numeroCompte, double decouvertAutorise){
        super(titulaire, solde, numeroCompte);
        this.decouvertAutorise = decouvertAutorise ;
    }

    public Double getDecouvertAutorise() {
        return decouvertAutorise;
    }
    
    public void setDecouvertAutorise(Double decouvertAutorise) {
        this.decouvertAutorise = decouvertAutorise;
    }

    @Override
    public void retirer(Double montant){
        if (montant > 0 && (solde-montant)>= -decouvertAutorise) {
            solde -= montant ;
            System.out.println("Retrait effectué !") ;
        }else{
            System.out.println("Retrait non effectué !") ;
        }
    }
}

class Banque {
    private List<CompteBancaire> comptes;

    // Constructeur
    Banque() {
        this.comptes = new ArrayList<>();
    }

    // Getter (retourne une copie pour protéger l'original)
    public List<CompteBancaire> getComptes() {
        return new ArrayList<>(comptes);
    }

    // Ajouter un compte
    public void ajouterCompte(CompteBancaire compte) {
        comptes.add(compte);
        System.out.println("Compte " + compte.getNumeroCompte() + " ajouté. Total : " + comptes.size() + " comptes");
    }

    // Afficher tous les comptes
    public void afficherTouslesComptes() {
        if (comptes.isEmpty()) {
            System.out.println("Aucun compte dans la banque !");
            return;
        }
        
        System.out.println("\n===== " + comptes.size() + " Comptes =====");
        for (CompteBancaire compte : comptes) {
            compte.afficherInfos();
        }
    }

    // Trouver un compte par son numéro
    public CompteBancaire trouverCompte(String numero) {
        for (CompteBancaire compte : comptes) {
            if (compte.getNumeroCompte().equals(numero)) {
                return compte;
            }
        }
        return null;
    }

    // Transférer de l'argent entre deux comptes
    public void transferer(String src, String dest, double montant) {
        // Vérification du montant
        if (montant <= 0) {
            System.out.println("Montant invalide ! Le montant doit être positif.");
            return;
        }

        CompteBancaire source = trouverCompte(src);
        CompteBancaire destination = trouverCompte(dest);

        if (source == null) {
            System.out.println("Compte source " + src + " introuvable !");
            return;
        }

        if (destination == null) {
            System.out.println("Compte destination " + dest + " introuvable !");
            return;
        }

        // Vérifier si le retrait est possible avant de transférer
        Double soldeSource = source.getSolde();
        
        // Cas spécial pour compte courant avec découvert
        if (source instanceof CompteCourant) {
            CompteCourant cc = (CompteCourant) source;
            if ((soldeSource - montant) < -cc.getDecouvertAutorise()) {
                System.out.println("Transfert impossible : découvert dépassé !");
                return;
            }
        } else {
            // Compte normal ou épargne
            if (montant > soldeSource) {
                System.out.println("Transfert impossible : solde insuffisant !");
                return;
            }
        }

        // Effectuer le transfert
        source.retirer(montant);
        destination.deposer(montant);
        System.out.println("Transfert de " + montant + " € effectué avec succès !");
    }

    // Supprimer un compte
    public boolean supprimerCompte(String numeroCompte) {
        CompteBancaire compte = trouverCompte(numeroCompte);
        if (compte != null) {
            comptes.remove(compte);
            System.out.println("Compte " + numeroCompte + " supprimé !");
            return true;
        }
        System.out.println("Compte " + numeroCompte + " introuvable !");
        return false;
    }

    // Afficher le solde total de la banque
    public double getSoldeTotal() {
        double total = 0;
        for (CompteBancaire compte : comptes) {
            total += compte.getSolde();
        }
        return total;
    }

    // Afficher les comptes par type
    public void afficherComptesCourants() {
        System.out.println("\n=== COMPTES COURANTS ===");
        for (CompteBancaire compte : comptes) {
            if (compte instanceof CompteCourant) {
                compte.afficherInfos();
            }
        }
    }

    public void afficherComptesEpargne() {
        System.out.println("\n=== COMPTES ÉPARGNE ===");
        for (CompteBancaire compte : comptes) {
            if (compte instanceof CompteEpargne) {
                compte.afficherInfos();
            }
        }
    }

    // Appliquer les intérêts à tous les comptes épargne
    public void appliquerInteretsATous() {
        for (CompteBancaire compte : comptes) {
            if (compte instanceof CompteEpargne) {
                ((CompteEpargne) compte).appliquerInteret();
            }
        }
        System.out.println("Intérêts appliqués à tous les comptes épargne !");
    }
}