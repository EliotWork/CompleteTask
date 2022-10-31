package com.basakkandemir.quizmasters;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {

    private static List<QuestionsList> artQuestions(){

        final List<QuestionsList> questionsLists = new ArrayList<>();

        final QuestionsList question1 =
                new QuestionsList("Oscar ödülü alan ilk kadın yönetmen kimdir?",
                        "Kathryn Bigelow", "Nora Ephron","Vera Chytilova",
                        "Tomris Giritlioğlu","Kathryn Bigelow","");
        final QuestionsList question2 =
                new QuestionsList("Hababam Sınıfı kimin eseridir?",
                        "Aziz Nesin", "Halit Ziya Uşaklıgil","Reşat Nuri Gültekin",
                        "Rıfat Ilgaz", "Rıfat Ilgaz","");
        final QuestionsList question3 =
                new QuestionsList("Romen rakamlarında hangi sayı yoktur?",
                        "100", "10","0",
                        "50","0", "");
        final QuestionsList question4 =
                new QuestionsList("Eurovision şarkı yarışmasına 1975 yılında ilk hangi ünlü isim katılmıştır?",
                        "Ajda Pekkan – Petrol", "Semiha Yankı – Seninle Bir Dakika","Çetin Alp – Opera",
                        "Nilüfer ve Grup Nazar – Sevince","Semiha Yankı – Seninle Bir Dakika", "");
        final QuestionsList question5 =
                new QuestionsList("Sinekli Bakkal romanının yazarı aşağıdakilerden hangisidir?",
                        "Reşat Nuri Güntekin", "Halide Edip Adıvar","Ziya Gökalp",
                        "Ömer Seyfettin","Halide Edip Adıvar", "");
        final QuestionsList question6 =
                new QuestionsList("Aşağıda verilen ilk çağ uygarlıklarından hangisi yazıyı bulmuştur?",
                        "Hititler", "Elamlar ","Sümerler",
                        "Urartular","Sümerler", "");
        final QuestionsList question7 =
                new QuestionsList("Hangisi Mimar Sinan'ın eseridir?",
                        "Selimiye Camii", "Bursa Ulu Camii","Sultan Ahmet Camii",
                        "Ayasofya","Selimiye Camii", "");
        final QuestionsList question8 =
                new QuestionsList("İstanbul'a gelerek Fatih Sultan Mehmet'in portresini yapan ünlü İtalyan ressam kimdir?",
                        "Sandro Botticelli", "Gentile Bellini","Donato Bramente",
                        "Giorgio Vasari","Gentile Bellini","");
        final QuestionsList question9 =
                new QuestionsList("Aşağıdakilerden hangisi sürrealist bir ressamdır?",
                        "Edgar Degas", "Pablo Picasso","Salvador Dali",
                        "Pierre Auguste Renoir","Salvador Dali", "");
        final QuestionsList question10 =
                new QuestionsList("Bazı istisnalar dışında müzeler genel olarak haftanın hangi günü kapalıdır?",
                        "Perşembe", "Cumartesi","Pazartesi",
                        "Çarşamba","Pazartesi", "");

        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);
        questionsLists.add(question6);
        questionsLists.add(question7);
        questionsLists.add(question8);
        questionsLists.add(question9);
        questionsLists.add(question10);

        return questionsLists;
    }

    private static List<QuestionsList> eglenceQuestions(){

        final List<QuestionsList> questionsLists = new ArrayList<>();

        final QuestionsList question1 =
                new QuestionsList("Hangisi genellikle çiğ deniz ürünlerinden ve pirinçten yapılan geleneksel Japon yemeğidir?",
                        "Sushi", "Sashimi","Wasabi",
                        "Tempura","Sushi","");
        final QuestionsList question2 =
                new QuestionsList("Hangi şehir savaş esnasında bir tahta at yardımıyla işgal edilmişti?",
                        "Miken", "Atina","Truva",
                        "Sparta", "Truva","");
        final QuestionsList question3 =
                new QuestionsList("Cristiano Ronaldo kimdir?",
                        "Yüzücü", "Futbolcu","Hokey Oyuncusu",
                        "Tenisçi","Futbolcu", "");
        final QuestionsList question4 =
                new QuestionsList("Babanızın babasının kızı sizin neyiniz olur?",
                        "Hala", "Teyze","Kuzen",
                        "Yenge","Hala", "");
        final QuestionsList question5 =
                new QuestionsList("Bir yarışta ikinciyi geçerseniz kaçıncı olursunuz ?",
                        "Üçüncü", "Birinci","Sonuncu",
                        "İkinci","Birinci", "");
        final QuestionsList question6 =
                new QuestionsList("Facebook hangi üniversitede kurulmuştur?",
                        "Harvard", "Oxford ","Cambridge",
                        "Stanford","Harvard", "");
        final QuestionsList question7 =
                new QuestionsList("İstanbul’un en kalabalık ilçesinin hangisidir?",
                        "Taksim", "Esenyurt","Fatih",
                        "Pendik","Esenyurt", "");
        final QuestionsList question8 =
                new QuestionsList("Kaç yılda bir Şubat ayı 29 çeker?",
                        "2", "3","4",
                        "5","4","");
        final QuestionsList question9 =
                new QuestionsList("Ülkemizde öğrenciler için tatil ayları denilince akla gelen hangileridir?",
                        "Ocak-Şubat-Mart", "Eylül-Ekim-Kasım","Haziran-Temmuz-Ağustos",
                        "Ağustos-Eylül-Ekim","Haziran-Temmuz-Ağustos", "");
        final QuestionsList question10 =
                new QuestionsList("Güneş sisteminde kaç gezegen vardır?",
                        "8", "9","10",
                        "15","8", "");

        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);
        questionsLists.add(question6);
        questionsLists.add(question7);
        questionsLists.add(question8);
        questionsLists.add(question9);
        questionsLists.add(question10);

        return questionsLists;

    }

    private static List<QuestionsList> sporQuestions(){

        final List<QuestionsList> questionsLists = new ArrayList<>();

        final QuestionsList question1 =
                new QuestionsList("Aşağıdakilerden hangisi halterci değildir?",
                        "Naim Süleymanoğlu", "Nurcan Taylan","Emre Sakıcı",
                        "Halil Mutlu","Emre Sakıcı","");
        final QuestionsList question2 =
                new QuestionsList("Forehand ve backhand hangi sporda topa vuruş şekilleridir?",
                        "Tenis", "Golf","Bilardo",
                        "Bowling", "Tenis","");
        final QuestionsList question3 =
                new QuestionsList("Cristiano Ronaldo kimdir?",
                        "Yüzücü", "Futbolcu","Hokey Oyuncusu",
                        "Tenisçi","Futbolcu", "");
        final QuestionsList question4 =
                new QuestionsList("Üzerinde boks yapılan,çevresi kordonlarla çevrilmiş yere ne ad verilir?",
                        "Kort", "Ring","Pist",
                        "Kafes","Ring", "");
        final QuestionsList question5 =
                new QuestionsList("Yüzme ve atletizm yarışlarında her sporcuya ayrılan şeride ne denir?",
                        "Peron", "Apron","Terminal",
                        "Kulvar","Kulvar", "");
        final QuestionsList question6 =
                new QuestionsList("Steps yaptı denilen bir sporcu hangi branşı yapıyordur?",
                        "Basketbol", "Voleybol ","Atletizm",
                        "Tenis","Basketbol", "");
        final QuestionsList question7 =
                new QuestionsList("Wimbledon tenis turnuvası hangi ülkede yapılır?",
                        "Fransa", "Avusturya","Amerika",
                        "İngiltere","İngiltere", "");
        final QuestionsList question8 =
                new QuestionsList("Sporun herhangi bir dalında,bir dereceye ulaşıp bu dereceyi tekrarlamaya ne denir?",
                        "Diskalifiye", "Egale","Rekor",
                        "Fodepar","Egale","");
        final QuestionsList question9 =
                new QuestionsList("Roger Federer hangi sporu yapmaktadır?",
                        "Tenis", "Golf","Basketbol",
                        "Voleybol","Tenis", "");
        final QuestionsList question10 =
                new QuestionsList("Basketbolda oyun başında yapılan hava atışına genelde hangi oyuncular çıkar?",
                        "Kaptanlar", "En Başarılar","En Skorer Oyunlar",
                        "En Uzun Boylular","En Uzun Boylular", "");

        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);
        questionsLists.add(question6);
        questionsLists.add(question7);
        questionsLists.add(question8);
        questionsLists.add(question9);
        questionsLists.add(question10);

        return questionsLists;

    }

    private static List<QuestionsList> scienceQuestions(){

        final List<QuestionsList> questionsLists = new ArrayList<>();

        final QuestionsList question1 =
                new QuestionsList("Türk aslanı olarak bilinen Sivas ilimizde meşhur çoban köpeğinin cinsi nedir?",
                        "Pitbull", "Doberman","Kangal",
                        "Fini","Kangal","");
        final QuestionsList question2 =
                new QuestionsList("Hangi hayvan uçan bir memelidir?",
                        "Yarasa", "Serçe","Kartal",
                        "Akbaba", "Yarasa","");
        final QuestionsList question3 =
                new QuestionsList("Yağ dolu bir kaba su döktüğümüzde nasıl bir durumla karşılaşırız?",
                        "Su altta kalır, yağ üste çıkar.", "Yağ kaybolur.","Yağ renk değiştirir.",
                        "Su yüzeye çıkar,yağ altta kalır.","Su altta kalır, yağ üste çıkar.", "");
        final QuestionsList question4 =
                new QuestionsList("Hızla giden bir treninin içinde havaya zıplayan bir kişinin yere düştüğündeki konumu nasıl olur?",
                        "Zıpladığı yerden daha geriye düşer.", "Zıpladığı yerden daha ileriye düşer.","Aynı yere düşer.",
                        "Düşmez.","Aynı yere düşer.", "");
        final QuestionsList question5 =
                new QuestionsList("Altın elementinin sembolü nedir?",
                        "Au", "Ag","He",
                        "Li","Au", "");
        final QuestionsList question6 =
                new QuestionsList("Aspirinin hammaddesi nedir?",
                        "Söğüt ", "Köknar ","Kavak ",
                        "Meşe","Köknar", "");
        final QuestionsList question7 =
                new QuestionsList("Aşağıdaki maddelerden hangisi bulunduğu kabın şeklini alır?",
                        "Katı", "Sıvı","Gaz",
                        "Plazma","Sıvı", "");
        final QuestionsList question8 =
                new QuestionsList("Su kaç derecede donma noktasına ulaşır?",
                        "-10", "0","2",
                        "-50","0","");
        final QuestionsList question9 =
                new QuestionsList("Elektrik akımı ölçü birimi hangisidir?",
                        "Direnç", "Amper","Voltmetre",
                        "Ohm","Amper", "");
        final QuestionsList question10 =
                new QuestionsList("Hangisi bir devre elemanı değildir?",
                        "Ampul", "Bağlantı Kablosu","Avize",
                        "Anahtar","Avize", "");

        questionsLists.add(question1);
        questionsLists.add(question2);
        questionsLists.add(question3);
        questionsLists.add(question4);
        questionsLists.add(question5);
        questionsLists.add(question6);
        questionsLists.add(question7);
        questionsLists.add(question8);
        questionsLists.add(question9);
        questionsLists.add(question10);

        return questionsLists;

    }

    public static List<QuestionsList> getQuestions(String selectedTopicName){

        switch (selectedTopicName){
            case "Sanat":
                return artQuestions();
            case "Eğlence":
                return eglenceQuestions();
            case "Spor":
                return sporQuestions();
            default:
                return scienceQuestions();
        }
    }



}
