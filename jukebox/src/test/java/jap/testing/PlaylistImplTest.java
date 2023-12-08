package jap.testing;
import org.junit.*;

import org.niit.com.ConnectionManager;
import org.niit.com.Playlist;
import org.niit.com.PlaylistImpl;
import org.niit.com.SongImpl;

import java.sql.SQLException;

import static org.junit.Assert.*;


public class PlaylistImplTest {
    PlaylistImpl test;
    //setUp and tearDown
    @Before
    public void setUp(){
        test=new PlaylistImpl();
        ConnectionManager.createConnection();
    }
    @After
    public void tearDown(){
        test=null;
    }
    @Test
    public void isSongInPlaylistSuccess() throws SQLException {
        assertEquals(true, PlaylistImpl.isSongInPlaylist(1,1));
    }
    @Test
    public void isSongInPlaylistFailure() throws SQLException {
        assertEquals(false,test.isSongInPlaylist(10,10));
    }
    @Test
    public void isSongExitsSuccess() throws SQLException{
        assertEquals(true, 1);
    }
    @Test
    public  void isSongExistsFailure() throws  SQLException{
        assertEquals(false,99);
    }
}
