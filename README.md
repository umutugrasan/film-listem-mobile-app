# Film Listem

Mobil Programlama dersi dönem sonu ödevi. Kullanıcının film/dizi izleme listesini tuttuğu Android uygulamasıdır.

## Teslim İçeriği

Ödev ZIP dosyasının içinde şu klasörler bulunur:

- **Odev/** — Android Studio'da geliştirilen Java kodları ve kaynak dosyaları
- **apk/** — Doğrudan kuruluma hazır APK dosyası
- **Ekran Görüntüleri/** — Firebase veritabanının aktif çalıştığına dair ekran görüntüleri

**Hızlı test için:** `apk/` klasöründeki APK dosyasını bir Android telefona kopyalayıp kurmanız yeterlidir. Ekstra kurulum, anahtar girme veya yapılandırma gerekmez — Firebase ve OMDb API ayarları APK içerisine gömülüdür.

## Özellikler

- Film/dizi arama (OMDb API ile)
- Listeme ekleme / listemden çıkarma
- "İzledim" işaretleme
- Profil bilgileri (ad, favori tür, hakkımda)
- Tüm veriler bulut veritabanında (Firebase Firestore) tutulur

## Kullanılan Teknolojiler

- **Dil:** Java
- **4 farklı Activity:**
  - `MainActivity` — Film listesi (ana ekran)
  - `SearchActivity` — Film/dizi arama
  - `DetailActivity` — Film detayı
  - `ProfileActivity` — Profil ve ayarlar
- **Veritabanı:** Firebase Firestore (bulut) + SharedPreferences (yerel — profil için)
- **Dış servis (API):** OMDb API
- **UI:** Material Components, RecyclerView, CardView
- **Görsel yükleme:** Glide
- **Ağ kütüphanesi:** Retrofit + Gson
- **Tema:** IMDb stili sarı-siyah koyu tema

## APK ile Hızlı Kurulum (Önerilen)

1. `apk/` klasöründeki APK dosyasını bir Android telefona kopyalayın (USB, e-mail, WhatsApp vb.)
2. Telefonda APK dosyasına dokunun
3. Çıkan uyarıda "Bilinmeyen kaynaklardan yüklemeye izin ver" seçeneğini onaylayın
4. Uygulamayı kurun, açın, kullanın

Tüm Firebase ve OMDb API ayarları APK içerisindedir — ek bir adım gerekmez.

## Kaynak Koddan Çalıştırma (İsteğe Bağlı)

Eğer kaynak kodu Android Studio'da derlemek isterseniz:

### 1) Android Studio'da Aç
- `Odev/` klasörünü Android Studio'da **File → Open** ile açın
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
- Internet bağlantısı gereklidir (Firestore ve OMDb API için)
