import 'package:flutter/widgets.dart';
import 'package:get/get.dart';
import 'package:kaunta/home/globales.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/utils/api_call.dart';
import 'package:kaunta/widgets/widgets.dart';

Widget verContadores(BuildContext contexto) => Obx(
      () {
        if (Globales().conectado) {
          ApiCall().getContadores(true);
        }

        return Container(
          margin: const EdgeInsets.all(25),
          child: ListView.builder(
            itemCount: Listado()
                .usuario
                .value
                .grupos![Listado().gActual]
                .counters!
                .length,
            itemBuilder: (context, index) => Obx(
              () => Listado()
                      .usuario
                      .value
                      .grupos![Listado().gActual]
                      .counters![index]
                      .active!
                      .value
                  ? cCardItemContador(
                      Listado()
                          .usuario
                          .value
                          .grupos![Listado().gActual]
                          .counters![index],
                      index,
                      contexto,
                    )
                  : const SizedBox(height: 1),
            ),
          ),
        );
      },
    );
