import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/paginas/nuevo_contador.dart';
import 'package:kaunta/paginas/ver_contadores.dart';
import 'package:kaunta/themes/temas.dart';
import 'package:shared_preferences/shared_preferences.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    RxInt pagina = 0.obs;
    var paginas = [
      const VerContadores(),
      const NuevoContador(),
    ];

    return Obx(
      () => MaterialApp(
        debugShowCheckedModeBanner: false,
        theme: ThemeData(primarySwatch: Temas().getPrimary()),
        home: Scaffold(
          appBar: AppBar(),
          bottomNavigationBar: NavigationBar(
            backgroundColor: Temas().getBackground(),
            labelBehavior: NavigationDestinationLabelBehavior.alwaysHide,
            elevation: 200,
            height: 60,
            destinations: const [
              NavigationDestination(
                icon: Icon(Icons.home, color: Colors.blueGrey),
                label: 'Contadores',
              ),
              NavigationDestination(
                icon: Icon(Icons.add, color: Colors.blueGrey),
                label: 'Nuevo contador',
              ),
            ],
            onDestinationSelected: (int selected) => pagina.value = selected,
            selectedIndex: pagina.value,
          ),
          body: Center(
            child: paginas[pagina.value],
          ),
        ),
      ),
    );
  }

  changeTheme(BuildContext context) async {
    final prefs = await SharedPreferences.getInstance();

    Widget chip(bool isSelected, String texto, int valor) {
      bool seleccionado = isSelected;
      return ChoiceChip(
        label: Text(
          texto,
          style: TextStyle(
              color: seleccionado == true ? Colors.white : Colors.black),
        ),
        selected: seleccionado,
        selectedColor: Temas().getPrimary(),
        onSelected: (value) {
          if (value) {
            Temas().actual.value = valor;
            seleccionado = value;
            prefs.setInt('temaActual', valor);
          }
        },
      );
    }

    showModalBottomSheet(
      context: context,
      builder: (BuildContext context) {
        return Obx(
          () => SingleChildScrollView(
            child: Container(
              color: Temas().getBackground(),
              child: SizedBox(
                height: 150,
                child: Container(
                  margin: const EdgeInsets.all(20),
                  child: Center(
                    child: Column(
                      children: [
                        Row(
                          children: [
                            Expanded(
                              flex: 3,
                              child:
                                  chip(Temas().actual.value == 1, 'Claro', 1),
                            ),
                            Expanded(
                              flex: 3,
                              child:
                                  chip(Temas().actual.value == 2, 'Oscuro', 2),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                ),
              ),
            ),
          ),
        );
      },
    );
  }
}
