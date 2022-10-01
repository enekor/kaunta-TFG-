class EditContador {
  int? id;
  String? name;
  int? counter;
  String? descripcion;

  EditContador({this.id, this.name, this.counter, this.descripcion});

  EditContador.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    counter = json['counter'];
    descripcion = json['descripcion'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name;
    data['counter'] = counter;
    data['descripcion'] = descripcion;
    return data;
  }
}
