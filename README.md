# Film Listem

Mobil Programlama dersi dönem sonu ödevi. Kullanıcının film/dizi izleme listesini tuttuğu basit bir Android uygulamasıdır.

## Teslim İçeriği

Bu ödev tek ZIP dosyası halinde yüklenmiştir:

- `film-listem.apk` — Doğrudan kuruluma hazır uygulama dosyası
- `kaynak-kod.zip` (veya kaynak kod klasörü) — Tüm Java/XML dosyaları, incelenebilir kaynak kodu

**Hızlı test için:** `film-listem.apk` dosyasını bir Android telefona kopyalayıp kurmanız yeterlidir. Ekstra kurulum, anahtar girme veya yapılandırma gerekmez — Firebase ve OMDb API ayarları APK içerisine gömülüdür.

## Özellikler

- Film/dizi ara (OMDb API ile)
- Listeme ekle / listemden çıkar
- Hangi filmi izledim işaretle
- Profil bilgileri (ad, favori tür, hakkımda)
- Tüm veriler bulut veritabanında (Firebase Firestore) tutulur

## Kullanılan Teknolojiler

- Dil: Java
- 4 farklı Activity:
  - `MainActivity` — Film listesi
  - `SearchActivity` — Film/dizi arama
  - `DetailActivity` — Film detayı
  - `ProfileActivity` — Profil ve ayarlar
- Veritabanı: Firebase Firestore (bulut) + SharedPreferences (yerel — profil için)
- Dış servis (API): OMDb API
- UI: Material Components, RecyclerView, CardView
- Görsel yükleme: Glide
- Ağ kütüphanesi: Retrofit + Gson

## APK ile Hızlı Kurulum (Önerilen)

1. `film-listem.apk` dosyasını bir Android telefona kopyalayın (USB, e-mail, WhatsApp vb.)
2. Telefonda APK dosyasına dokunun
3. Çıkan uyarıda "Bilinmeyen kaynaklardan yüklemeye izin ver" seçeneğini onaylayın
4. Uygulamayı kurun, açın, kullanın

Tüm Firebase ve OMDb API ayarları APK içerisindedir — ek bir adım gerekmez.

## Kaynak Koddan Çalıştırma (İsteğe Bağlı)

Eğer kaynak kodu Android Studio'da derlemek isterseniz:

### 1) Android Studio'da Aç
- Kaynak kod ZIP'ini açın
- Android Studio'da **File → Open** ile proje klasörünü seçin
- Gradle Sync tamamlanmasını bekleyin

### 2) OMDb API Anahtarı
- OMDb API anahtarı zaten `app/src/main/java/com/example/odev/Constants.java` dosyasında tanımlıdır
- Kendi anahtarınızı kullanmak isterseniz: [https://www.omdbapi.com/apikey.aspx](https://www.omdbapi.com/apikey.aspx) adresinden ücretsiz alıp `OMDB_API_KEY` değerini değiştirin

### 3) Firebase Bağlantısı
Kaynak kodu kendi Firebase projenizle çalıştırmak isterseniz:
- [https://console.firebase.google.com](https://console.firebase.google.com) adresinden yeni bir proje açın
- Android uygulama ekleyin, package adı: `com.example.odev`
- `google-services.json` dosyasını indirip `app/` klasörüne yerleştirin
- Firestore Database'i Test modunda başlatın

### 4) Çalıştır
- Emülatör veya gerçek cihazda **Run ▶** ile çalıştırın

## Ekran Yapısı

1. **Ana Ekran (Listem):** Eklenen filmler RecyclerView üzerinde liste halinde gösterilir. Sağ üstte profil ikonu, sağ altta yeni film ekleme butonu (FAB) bulunur.
2. **Arama Ekranı:** OMDb API üzerinden film/dizi araması yapılır, sonuçlar liste halinde gösterilir.
3. **Detay Ekranı:** Seçilen filmin posteri, IMDb puanı, özeti gösterilir. "Listeme Ekle / Çıkar" ve "İzledim" işlemleri buradan yapılır.
4. **Profil Ekranı:** Kullanıcı adı, favori tür ve hakkımda alanları doldurulur. Hem yerel (SharedPreferences) hem buluta (Firestore) kaydedilir.

## Not

- Min SDK: 24 (Android 7.0)
- Target SDK: 36
