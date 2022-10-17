import 'package:flutter/widgets.dart';
import 'package:get/get.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/widgets/widgets.dart';

Widget verContadores() => Obx(
      () => Container(
        margin: const EdgeInsets.all(25),
        child: ListView.builder(
          itemCount: Listado().gActual.counters!.length,
          itemBuilder: (context, index) => Obx(
            () => Listado().gActual.counters![index].active!.value
                ? cCardItemContador(
                    Listado().gActual.counters![index],
                    index,
                    context,
                  )
                : const SizedBox(height: 1),
          ),
        ),
      ),
    );
