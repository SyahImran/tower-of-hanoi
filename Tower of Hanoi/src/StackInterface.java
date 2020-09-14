public interface StackInterface<E>
{
    public E push(E o);
    public E peek();
    public E pop();
    public boolean empty();
    public int size();
    public E get(int x);
}