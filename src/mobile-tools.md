# Выбор средства разработки приложения
----------
### Контекст
Проект ориентирован на создание работающего оффлайн мобильного приложения с обработкой фото нейросетевой моделью. Модель будет являться составной частью самого приложения. В России пользовтелей Android - 64%, iOS - 35% [1]. Также стоит отметить факт отсутсвия у разработчиков устройств на базе iOS.
### Рассматриваемые варианты
- Jetpack Compose - фреймворк для Android на базе языка Kotlin;
- SwiftUI - фреймворк от Apple на базe языка Swift для создания пользовательских интерфейсов на всех платформах Apple;
- Flutter - кроссплатформенный фреймворк на базе языка Dart;
- React Native - кроссплатформенный фреймворк для создания мобильных приложений на JavaScript и React.
### Решение
Jetpack Compose
### Обоснование
- У команды разработчиков нет опыта по работе с языком Swift;
- У команды разработчиков нет в начилии ни одно устройства на базе iOS, публикация приложения на этой платформе не представляется возможной. Средств на приобретение устройств Apple также нет;
- Нативные решения (Jetpack compose для Android) в среднем меньше нагружают процессор и меньше расходуют память мобильных устрйоств, чем кроссплатформенные решения [2] (Flutter [3] и React Native [2]). Это фактор важен, так как работа нейросетевой модели будет осуществляться на самом устройстве.
### Последствия
- ✅ Приложение будет эффективнее использовать ресурсы мобильного устройста;
- ✅ Приложением смогут пользоваться более 60% российских пользователей;
- ❌ Для пользователей с iOS (35%) приложение будет не доступно.
----------
### Источники
1. statcounter - Mobile Operating System Market Share Russian Federation [Электронный ресурс]. Режим доступа: <https://gs.statcounter.com/os-market-share/mobile/russian-federation> (Дата обращения: 01.10.2025);
2. Thomas Dorfer, Lukas Demetz, Stefan Huber, Impact of mobile cross-platform development on CPU, memory and battery of mobile devices when using common mobile app features // Procedia Computer Science №175, 2020. P. 189-196. DOI: [10.1016/j.procs.2020.07.029](https://doi.org/10.1016/j.procs.2020.07.029);
3. Hina Hussain, Comparative Study of Android Native and Flutter App Development / Hina Hussain, Kamran Khan, Faiza Farooqui, Dr. Qasim Ali Arain, Dr. Isma Farah Siddiqui // KSII The 13th International Conference on Internet (ICONI), Jeju, South Korea, 2021. P. 99-101.