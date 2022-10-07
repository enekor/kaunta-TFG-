import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/paginas/contadores/nuevo_contador.dart';
import 'package:kaunta/paginas/contadores/ver_contadores.dart';
import 'package:kaunta/paginas/grupos/nuevo_grupo.dart';
import 'package:kaunta/paginas/grupos/ver_grupo.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/themes/temas.dart';

class SinConexion extends StatelessWidget {
  const SinConexion({super.key});

  @override
  Widget build(BuildContext context) {
    RxInt pagina = 0.obs;
    var paginas = [
      verGrupos(),
      nuevoGrupo(),
      verContadores(),
      nuevoContador(context)
    ];

    return Obx(
      () => Scaffold(
        backgroundColor: Temas().getBackground(),
        appBar: AppBar(
          backgroundColor: Temas().getPrimary(),
          title: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(
                "Kaunta  ",
                style: TextStyle(color: Temas().getTextColor()),
              ),
              const SizedBox(width: 12),
              Icon(
                Icons.wifi_off_rounded,
                color: Temas().getTextColor(),
              ),
            ],
          ),
          actions: [
            IconButton(
              onPressed: cambiarTema,
              icon: Icon(
                Temas().actual.value == 0
                    ? Icons.dark_mode_rounded
                    : Icons.light_mode_rounded,
                color: Temas().getTextColor(),
              ),
            ),
          ],
          leading: IconButton(
            onPressed: () => Listado().verGrupos.value
                ? Navigator.of(context).pop()
                : Listado().verGrupos.value = true,
            icon: const Icon(Icons.arrow_back_ios_new_rounded),
          ),
        ),
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
          child: Listado().verGrupos.value == true
              ? paginas[pagina.value]
              : paginas[pagina.value + 2],
        ),
      ),
    );
  }

  void cambiarTema() {
    if (Temas().actual.value == 1) {
      Temas().actual.value = 0;
    } else {
      Temas().actual.value = 1;
    }
  }
}
