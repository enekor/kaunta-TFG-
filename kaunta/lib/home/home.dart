import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/home/botones.dart';
import 'package:kaunta/home/globales.dart';
import 'package:kaunta/json.dart';
import 'package:kaunta/paginas/contadores/nuevo_contador.dart';
import 'package:kaunta/paginas/contadores/restore_contadores.dart';
import 'package:kaunta/paginas/contadores/ver_contadores.dart';
import 'package:kaunta/paginas/grupos/nuevo_grupo.dart';
import 'package:kaunta/paginas/grupos/restore_grupos.dart';
import 'package:kaunta/paginas/grupos/ver_grupo.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/themes/temas.dart';

void abrirPagina(BuildContext context) {
  Navigator.of(context).pushReplacement(
    MaterialPageRoute(
      builder: (context) => const Botones(),
    ),
  );
}

void onBackPressed(BuildContext context) {
  if (Globales().pagina.value != 0) {
    Globales().pagina.value = 0;
  }

  if (!Globales().verGrupos.value) {
    Globales().verGrupos.value = true;
  } else {
    //abrirPagina(context);
    Navigator.pop(context);
  }
}

class Home extends StatelessWidget {
  const Home({super.key});

  @override
  Widget build(BuildContext context) {
    var paginas = [
      verGrupos(context),
      nuevoGrupo(context),
      restoreGrupos(context),
      verContadores(context),
      nuevoContador(context),
      restoreContadores(context)
    ];

    return WillPopScope(
      onWillPop: () async {
        onBackPressed(context);
        return false;
      },
      child: FutureBuilder(
        future: loadCounters(),
        builder: ((context, snapshot) => Listado().leido.value == true
            ? Obx(
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
                      onPressed: () => Globales().verGrupos.value
                          ? onBackPressed(context)
                          : Globales().verGrupos.value = true,
                      icon: const Icon(Icons.arrow_back_ios_new_rounded),
                    ),
                  ),
                  bottomNavigationBar: NavigationBar(
                    backgroundColor: Temas().getBackground(),
                    labelBehavior:
                        NavigationDestinationLabelBehavior.alwaysHide,
                    elevation: 200,
                    height: 60,
                    destinations: const [
                      NavigationDestination(
                        icon: Icon(Icons.home, color: Colors.blueGrey),
                        label: 'Home',
                      ),
                      NavigationDestination(
                        icon: Icon(Icons.add, color: Colors.blueGrey),
                        label: 'Nuevo',
                      ),
                      NavigationDestination(
                        icon:
                            Icon(Icons.restore_rounded, color: Colors.blueGrey),
                        label: "Restore",
                      ),
                    ],
                    onDestinationSelected: (int selected) =>
                        Globales().pagina.value = selected,
                    selectedIndex: Globales().pagina.value,
                  ),
                  body: Container(
                    color: Temas().getBackground(),
                    child: Center(
                      child: Globales().verGrupos.value == true
                          ? paginas[Globales().pagina.value]
                          : paginas[Globales().pagina.value + 3],
                    ),
                  ),
                ),
              )
            : Container(
                color: Temas().getBackground(),
                child: const Center(
                  child: CircularProgressIndicator(),
                ),
              )),
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
