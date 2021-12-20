CREATE SCHEMA IF NOT EXISTS "kisiler";
CREATE SCHEMA IF NOT EXISTS "public";
CREATE SCHEMA IF NOT EXISTS "bina";

CREATE TABLE IF NOT EXISTS "kisiler"."kisi" (
"turkodu" CHAR(5) NOT NULL,
"ad" VARCHAR(20) NOT NULL,
"soyad" VARCHAR(20) NOT NULL,
"tur" VARCHAR(15) NOT NULL,
CONSTRAINT "kisiler_PK" PRIMARY KEY("turkodu")
);

CREATE TABLE IF NOT EXISTS "public"."iletisim_bilgileri" (
"kimlikno" CHAR(11) NOT NULL,
"turkodu" CHAR(5) NOT NULL,
"telno" CHAR(11) NOT NULL,
"eposta" VARCHAR(40),
CONSTRAINT "iletisim_bilgileri_PK" PRIMARY KEY("kimlikno"),
CONSTRAINT "iletisim_bilgileri_FK" FOREIGN KEY("turkodu") REFERENCES "kisiler"."kisi"("turkodu"),
CONSTRAINT "iletisim_bilgileri_Unique" UNIQUE("telno","eposta")
);

CREATE TABLE IF NOT EXISTS "kisiler"."sakin" (
"turkodu" CHAR(5) NOT NULL,
"binano" SMALLINT NOT NULL,
CONSTRAINT "sakin_PK" PRIMARY KEY("turkodu"),
CONSTRAINT "sakin_FK" FOREIGN KEY("turkodu") REFERENCES "kisiler"."kisi"("turkodu")
ON DELETE CASCADE
ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS "kisiler"."yonetici" (
"yoneticino" SERIAL NOT NULL,
"turkodu" CHAR(5) NOT NULL,
"mevcut_yonetici" BOOLEAN NOT NULL,
"donem" CHAR(9) NOT NULL,
CONSTRAINT "yonetici_PK" PRIMARY KEY("yoneticino"),
CONSTRAINT "yonetici_FK" FOREIGN KEY("turkodu") REFERENCES "kisiler"."sakin"("turkodu")
);

CREATE TABLE IF NOT EXISTS "kisiler"."personel" (
"turkodu" CHAR(5) NOT NULL,
"binano" SERIAL NOT NULL,
"maas" INT NOT NULL,
CONSTRAINT "personel_PK" PRIMARY KEY("turkodu"),
CONSTRAINT "personel_FK" FOREIGN KEY("turkodu") REFERENCES "kisiler"."kisi"("turkodu")
ON DELETE CASCADE
ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS "kisiler"."ziyaretci" (
"ziykodu" CHAR(4) NOT NULL,
"turkodu" CHAR(5) NOT NULL,
"giris" VARCHAR(30) NOT NULL,
"cıkıs" VARCHAR(30),
CONSTRAINT "ziyaretci_PK" PRIMARY KEY("ziykodu"),
CONSTRAINT "ziyaretci_FK" FOREIGN KEY("turkodu") REFERENCES "kisiler"."kisi"("turkodu")
ON DELETE CASCADE
ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS "kisiler"."misafir" (
"ziykodu" CHAR(4) NOT NULL,
"parselno" SERIAL NOT NULL,
CONSTRAINT "misafir_PK" PRIMARY KEY("ziykodu"),
CONSTRAINT "misafir_FK" FOREIGN KEY("ziykodu") REFERENCES "kisiler"."ziyaretci"("ziykodu")
ON DELETE CASCADE
ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS "public"."sirket" (
"sirketno" SERIAL NOT NULL,
"ad" VARCHAR(30) NOT NULL,
"tel" CHAR(11) NOT NULL,
"adres" VARCHAR(20) NOT NULL,
CONSTRAINT "sirket_PK" PRIMARY KEY("sirketno")
);

CREATE TABLE IF NOT EXISTS "kisiler"."sirket_temsilcisi" (
"ziykodu" CHAR(4) NOT NULL,
"sirketno" SERIAL NOT NULL,
"parselno" SERIAL NOT NULL,
CONSTRAINT "sirket_temsilcisi_PK" PRIMARY KEY("ziykodu"),
CONSTRAINT "sirket_temsilcisi_FK" FOREIGN KEY("ziykodu") REFERENCES "kisiler"."ziyaretci"("ziykodu")
ON DELETE CASCADE
ON UPDATE CASCADE,
CONSTRAINT "sirket_temsilcisi_FK2" FOREIGN KEY("sirketno") REFERENCES "public"."sirket"("sirketno")
);

CREATE TABLE IF NOT EXISTS "public"."evcil_hayvan" (
"no" SERIAL NOT NULL,
"sahip" CHAR(5) NOT NULL,
"tur" VARCHAR(20) NOT NULL,
CONSTRAINT "evcil_hayvan_PK" PRIMARY KEY("no"),
CONSTRAINT "evcil_hayvan_FK" FOREIGN KEY("sahip") REFERENCES "kisiler"."kisi"("turkodu")
);

CREATE TABLE IF NOT EXISTS "public"."sokak" (
"sokakno" SERIAL NOT NULL,
"ad" VARCHAR(30),
CONSTRAINT "sokak_PK" PRIMARY KEY("sokakno")
);

CREATE TABLE IF NOT EXISTS "public"."parsel" (
"parselno" SERIAL NOT NULL,
"sokakno" SERIAL NOT NULL,
CONSTRAINT "parsel_PK" PRIMARY KEY("parselno"),
CONSTRAINT "parsel_FK" FOREIGN KEY("sokakno") REFERENCES "public"."sokak"("sokakno")
);

CREATE TABLE IF NOT EXISTS "bina"."bina" (
"binano" SERIAL NOT NULL,
"parselno" SERIAL NOT NULL,
CONSTRAINT "bina_PK" PRIMARY KEY("binano"),
CONSTRAINT "bina_FK" FOREIGN KEY("parselno") REFERENCES "public"."parsel"("parselno")
);

CREATE TABLE IF NOT EXISTS "bina"."konut" (
"binano" SERIAL NOT NULL,
"kat" SMALLINT,
"oda" SMALLINT,
"salon" SMALLINT,
CONSTRAINT "konut_PK" PRIMARY KEY("binano"),
CONSTRAINT "konut_FK" FOREIGN KEY("binano") REFERENCES "bina"."bina"("binano")
ON DELETE CASCADE
ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS "bina"."ticari_ortak" (
"binano" SERIAL NOT NULL,
"tur" VARCHAR(30),
CONSTRAINT "tic_ort_PK" PRIMARY KEY("binano"),
CONSTRAINT "tic_ort_FK" FOREIGN KEY("binano") REFERENCES "bina"."bina"("binano")
ON DELETE CASCADE
ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS "public"."bina_bilgi" (
"no" SERIAL NOT NULL,
"binano" SERIAL NOT NULL,
"boyut" INT,
CONSTRAINT "bina_bilgi_PK" PRIMARY KEY("no"),
CONSTRAINT "bina_bilgi_FK" FOREIGN KEY("binano") REFERENCES "bina"."bina"("binano")
);

CREATE OR REPLACE PROCEDURE CikisYapmamisZiy ()
LANGUAGE plpgsql
AS
$$
BEGIN
    SELECT turkodu,ad,soyad,giris FROM kisiler.kisi INNER JOIN kisiler.ziyaretci ON Kisi.turkodu = Ziyaretci.turkodu WHERE ("cikis" == NULL);
END;
$$;

CREATE OR REPLACE PROCEDURE HayvaniOlanKisiler ()
LANGUAGE plpgsql
AS
$$
BEGIN
    SELECT turkodu,ad,soyad FROM kisiler.kisi INNER JOIN public.evcil_hayvanlar ON Kisi.turkodu = EvcilHayvan.sahip;
END;
$$;

CREATE OR REPLACE FUNCTION CikisYapmamisZiySayisi ()
RETURNS INT
LANGUAGE plpgsql
AS
$$
DECLARE
    sonuc INT;
BEGIN
    sonuc := (SELECT COUNT(*) FROM CikisYapmamisZiy());
    RETURN sonuc;
END;
$$;

CREATE OR REPLACE PROCEDURE TumTelNolar ()
LANGUAGE plpgsql
AS
$$
BEGIN
    SELECT telno FROM public.iletisim_bilgileri;
END;
$$;

CREATE TABLE IF NOT EXISTS "public"."soyad_degisimi_takip" (
"turkodu" CHAR(5) NOT NULL,
"eski_soyad" VARCHAR(20),
"yeni_soyad" VARCHAR(20),
"degisiklik_tarihi" TIMESTAMP NOT NULL
);

CREATE OR REPLACE FUNCTION "soyad_degisimi"()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS
$$
BEGIN
    IF NEW.soyad <> OLD.soyad THEN
        INSERT INTO soyad_degisimi_takip(turkodu,eski_soyad,yeni_soyad,degisiklik_tarihi)
        VALUES(OLD.turkodu,OLD.soyad,NEW.soyad,CURRENT_TIMESTAMP::TIMESTAMP);
	END IF;
	RETURN NEW;
END;
$$;

CREATE OR REPLACE TRIGGER "soyad_degistiginde"
BEFORE UPDATE ON "kisiler"."kisi"
FOR EACH ROW EXECUTE PROCEDURE "soyad_degisimi"();

CREATE TABLE IF NOT EXISTS "public"."tasinma_takip" (
"turkodu" CHAR(5) NOT NULL,
"eski_bina" SMALLINT,
"yeni_bina" SMALLINT,
"tasinma_tarihi" TIMESTAMP NOT NULL
);

CREATE OR REPLACE FUNCTION "siteici_tasinma"()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS
$$
BEGIN
    IF NEW.binano <> OLD.binano THEN
        INSERT INTO tasinma_takip(turkodu,eski_bina,yeni_bina,tasinma_tarihi)
        VALUES(OLD.turkodu,OLD.binano,NEW.binano,CURRENT_TIMESTAMP::TIMESTAMP);
	END IF;
	RETURN NEW;
END;
$$;

CREATE OR REPLACE TRIGGER "tasinildiginda"
BEFORE UPDATE ON "kisiler"."sakin"
FOR EACH ROW EXECUTE PROCEDURE "siteici_tasinma"();

CREATE OR REPLACE TRIGGER "tasinildiginda"
BEFORE UPDATE ON "kisiler"."personel"
FOR EACH ROW EXECUTE PROCEDURE "siteici_tasinma"();

CREATE TABLE IF NOT EXISTS "public"."gelistirme_takip" (
"binano" SERIAL NOT NULL,
"eski_kat" SMALLINT,
"yeni_kat" SMALLINT,
"eski_oda" SMALLINT,
"yeni_oda" SMALLINT,
"eski_salon" SMALLINT,
"yeni_salon" SMALLINT,
"gelistirme_tarihi" TIMESTAMP NOT NULL
);

CREATE OR REPLACE FUNCTION "konut_gelistirme"()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS
$$
BEGIN
    IF NEW.kat <> OLD.kat OR NEW.oda <> OLD.oda OR NEW.salon <> OLD.salon THEN
        INSERT INTO gelistirme_takip(binano,eski_kat,yeni_kat,eski_oda,yeni_oda,eski_salon,yeni_salon,gelistirme_tarihi)
        VALUES(OLD.binano,OLD.kat,NEW.kat,OLD.oda,NEW.oda,OLD.salon,NEW.salon,CURRENT_TIMESTAMP::TIMESTAMP);
	END IF;
	RETURN NEW;
END;
$$;

CREATE OR REPLACE TRIGGER "konuta_ilave_yapildiginda"
BEFORE UPDATE ON "bina"."konut"
FOR EACH ROW EXECUTE PROCEDURE "konut_gelistirme"();

CREATE TABLE IF NOT EXISTS "public"."insa_takip" (
"binano" SERIAL NOT NULL,
"parselno" SERIAL NOT NULL,
"insa_tarihi" TIMESTAMP NOT NULL 
);

CREATE OR REPLACE FUNCTION "bina_insa"()
RETURNS TRIGGER 
LANGUAGE plpgsql
AS
$$
BEGIN
    INSERT INTO insa_takip(binano,parselno,insa_tarihi)
    VALUES(NEW.binano,NEW.parselno,CURRENT_TIMESTAMP::TIMESTAMP);
	RETURN NEW;
END;
$$;

CREATE OR REPLACE TRIGGER "bina_insaatinda"
AFTER INSERT ON "bina"."bina"
FOR EACH ROW EXECUTE PROCEDURE "bina_insa"();

--INSERT INTO "kisiler"."kisi" VALUES ('S0001','Erim','Vurucu','Yönetici');
--INSERT INTO "kisiler"."sakin" VALUES ('S0001',8);
--INSERT INTO "public"."iletisim_bilgileri" VALUES ('S0001','27499999999','05394870309','erimvurucu@gmail.com');
--INSERT INTO "kisiler"."yonetici" VALUES (1,'S0001',t,'2021-2022');
--INSERT INTO "public"."sokak" VALUES (1,'Atatürk');
--INSERT INTO "public"."sokak" VALUES (2,'Vurucu');
--INSERT INTO "public"."parsel" VALUES (8,2);
--INSERT INTO "bina"."bina" VALUES (8,8);
--INSERT INTO "public"."bina_bilgi" VALUES (1,8,200);
--INSERT INTO "bina"."konut" VALUES (8,3,5,2);

