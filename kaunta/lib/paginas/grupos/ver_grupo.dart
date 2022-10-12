import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/themes/temas.dart';
import 'package:kaunta/widgets/widgets.dart';

Widget verGrupos() => Obx(
      () => Container(
        color: Temas().getBackground(),
        margin: const EdgeInsets.all(25),
        child: ListView.builder(
          itemCount: Listado().usuario.grupos!.value.length,
          itemBuilder: (context, index) => GestureDetector(
            onTap: () {
              Listado().gActual = Listado().usuario.grupos![index];
              Listado().verGrupos.value = false;
            },
            child: cGroupListItem(Listado().usuario.grupos![index], index),
          ),
        ),
      ),
    );
