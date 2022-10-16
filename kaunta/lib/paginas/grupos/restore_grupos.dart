import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/themes/temas.dart';
import 'package:kaunta/widgets/widgets.dart';

Widget restoreGrupos(BuildContext context) => Obx(
      () => Container(
        color: Temas().getBackground(),
        margin: const EdgeInsets.all(25),
        child: ListView.builder(
          itemCount: Listado().usuario.grupos!.length,
          itemBuilder: (context, index) => Obx(
            () => Listado().usuario.grupos![index].activo!.value == false
                ? cRestoreGroupCardItem(
                    Listado().usuario.grupos![index],
                    context,
                  )
                : const SizedBox(height: 1),
          ),
        ),
      ),
    );
