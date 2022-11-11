import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/home/botones.dart';
import 'package:kaunta/home/globales.dart';
import 'package:kaunta/json.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/widgets/widgets.dart';

Widget verGrupos(BuildContext context) => Container(
      margin: const EdgeInsets.all(25),
      child: Obx(
        () {
          loadCounters();

          return ListView.builder(
            itemCount: Listado().usuario.value.grupos!.length,
            itemBuilder: (context, index) =>
                Listado().usuario.value.grupos![index].activo!.value
                    ? GestureDetector(
                        onTap: () {
                          Listado().gActual = index;

                          Globales().verGrupos.value = false;
                        },
                        child: cGroupListItem(
                          Listado().usuario.value.grupos![index],
                          index,
                          context,
                        ),
                      )
                    : const SizedBox(
                        height: 1,
                      ),
          );
        },
      ),
    );
