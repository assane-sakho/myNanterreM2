package miage.parisnanterre.fr.mynanterre2;

import org.junit.Test;

import java.time.LocalDateTime;

import miage.parisnanterre.fr.mynanterre2.api.club.Club;
import miage.parisnanterre.fr.mynanterre2.api.club.Publication;
import miage.parisnanterre.fr.mynanterre2.api.club.SimpleClub;
import miage.parisnanterre.fr.mynanterre2.api.club.Type;
import miage.parisnanterre.fr.mynanterre2.api.user.User;

import static org.junit.Assert.assertEquals;

public class ClubUnitTest {

    @Test
    public void testCreationEmptyConstructor()
    {
        Club club = new Club();
        club.setName("club de test")
            .setDescription("une description")
            .setContact("des informations de contact")
            .setMail("uneAdresseMail@domaine.com")
            .setValidate(true)
            .setWebsite("www.monclub.fr");

        club.addPublication("une publication")
            .addPublication(new Publication("Nouvelle publication"))
            .addPublication("actualités du 12 décembre");

        assertEquals("club de test", club.getName());
        assertEquals("une description", club.getDescription());
        assertEquals("des informations de contact", club.getContact());
        assertEquals("uneAdresseMail@domaine.com", club.getMail());
        assertEquals(false, club.isCertificate());
        assertEquals(true, club.isValidate());
        assertEquals("www.monclub.fr", club.getWebsite());
        assertEquals(3, club.getPublications().size());
        assertEquals("une publication", club.getPublications().stream().findFirst().get().getMessage());
        assertEquals(LocalDateTime.now().toLocalDate(), club.getCreationDate().toLocalDate());
    }

    @Test
    public void testCreation()
    {
        Type type = new Type("Associations de culture artistique et scientifique / Médias");

        String description = "Cette association a pour but d'instaurer, de développer et d'animer une dynamique d'ouverture au sein de l'Université, de dynamiser la vie étudiante par des manifestations culturelles à destination de tous les étudiant·e·s et de procurer à ses membres des moyens de formation complémentaire de leur cursus universitaire. Cette association y contribue par une représentation véritablement indépendante de toute formation politique.\n" +
                "Passionnée de théâtre et persuadée que le rire adoucit les mœurs, l'association Article X organise depuis 2015 un festival de stand up étudiant, le Festival Arti'Show, permettant une véritable expression de la voix étudiante au travers de l'ironie et de la dérision, mais aussi favorisant le lien social et l'émancipation culturelle pour toutes et tous. Le festival vise à mettre en lien trois médias humoristiques : internet, la voix étudiante et le stand-up. Il a pour but de dénicher des talents étudiants et de les amener vers le professionnel en leur apportan";
        String imageUrl = "https://culture.parisnanterre.fr/medias/photo/articlex_1484751163917-png";

        String mail = "articleX.paris10@gmail.com";
        String website = "https://www.facebook.com/associationarticleX/";
        String contact = "Université Paris Nanterre\n" +
                "Maison de l'Étudiant (Bât. R) - Boîte postale n°13\n" +
                "200 avenue de la République\n" +
                "92000 Nanterre";

        Club club = new Club("Article X",
                "".getBytes(),
                imageUrl,
                LocalDateTime.now(),
                description,
                true,
                true,
                new User("Lewis", "Gleeson", new miage.parisnanterre.fr.mynanterre2.api.user.Type("utilisateur")),
                contact,
                mail,
                website,
                type);

        assertEquals(0, club.getId());
        assertEquals("Article X", club.getName());
        assertEquals(imageUrl, club.getImageUrl());
        assertEquals(description, club.getDescription());
        assertEquals(contact, club.getContact());
        assertEquals(mail, club.getMail());
        assertEquals(true, club.isCertificate());
        assertEquals(true, club.isValidate());
        assertEquals(website, club.getWebsite());
        assertEquals(type, club.getType());
        assertEquals(LocalDateTime.now().toLocalDate(), club.getCreationDate().toLocalDate());
    }

    @Test
    public void testDownCasting()
    {
        Club club = new Club();
        club.setName("club de test")
                .setDescription("une description")
                .setContact("des informations de contact")
                .setMail("uneAdresseMail@domaine.com")
                .setValidate(true)
                .setWebsite("www.monclub.fr");

        SimpleClub simpleClub = (SimpleClub)club;

        assertEquals(club.getId(), simpleClub.getId());
        assertEquals(club.getName(), simpleClub.getName());
        assertEquals(club.getDescription(), simpleClub.getDescription());
        assertEquals("des informations de contact", simpleClub.getContact());
        assertEquals(club.getMail(), simpleClub.getMail());
        assertEquals(club.isCertificate(), simpleClub.isCertificate());
        assertEquals(club.isValidate(), simpleClub.isValidate());
        assertEquals(club.getWebsite(), simpleClub.getWebsite());
        assertEquals(club.getCreationDate().toLocalDate(), simpleClub.getCreationDate().toLocalDate());
    }

    @Test
    public void testUpCasting()
    {
        SimpleClub simpleClub = new SimpleClub();
        simpleClub.setName("club de test")
                .setDescription("une description")
                .setContact("des informations de contact")
                .setMail("uneAdresseMail@domaine.com")
                .setValidate(true)
                .setWebsite("www.monclub.fr");

        Club club = new Club(simpleClub);

        assertEquals(0, club.getId());
        assertEquals("club de test", club.getName());
        assertEquals("une description", club.getDescription());
        assertEquals("des informations de contact", club.getContact());
        assertEquals("uneAdresseMail@domaine.com", club.getMail());
        assertEquals(false, club.isCertificate());
        assertEquals(true, club.isValidate());
        assertEquals("www.monclub.fr", club.getWebsite());
        assertEquals(LocalDateTime.now().toLocalDate(), club.getCreationDate().toLocalDate());
    }
}
