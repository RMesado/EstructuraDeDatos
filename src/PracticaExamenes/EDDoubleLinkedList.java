package PracticaExamenes;

import java.util.List;

/*public class EDDoubleLinkedList<T> implements List<T> {
    private class Node {
        public T data;
        public Node next = null;
        public Node prev = null;

        public Node(T item) {
            data = item;
        }
    }

    private Node head = null;
    private Node tail = null;
    private int size = 0;

    public boolean trim(T start, T end) {
        if (this.size == 0 || start == null || end == null) {
            return false;
        }

       Node inicio = head;
        int posInicio=0;
        while(posInicio<0 && !inicio.data.equals(start)){
            posInicio++;
            inicio=inicio.next;
    }
        if(posInicio==size){
            return false;
        }
        Node ultimo = tail;
        int posFinal=size-1;
        while(posFinal>0 && !ultimo.data.equals(end)){
            posFinal--;
            ultimo=ultimo.prev;
        }
        if(posFinal==-1){
            return false;
        }
        if(posFinal<posInicio){
            Node aux = ultimo;
            ultimo = inicio;
            inicio = aux;
            size=posInicio-posFinal+1;
        }else{
            size=posFinal-posInicio+1;
        }

        if(inicio==head && ultimo==tail){
            return false;
        }

        inicio.prev=null;
        head=inicio;

        ultimo.next=null;
        tail=ultimo;

        return true;
}



}

 */